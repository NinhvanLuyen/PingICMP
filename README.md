# PingICMP
```
   Observable<ItemPing> getData = Observable.create(sub -> {
            Process p;
            p = new ProcessBuilder("sh").redirectErrorStream(true).start();
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("ping -c 3 " + host + '\n');
            os.flush();
// Close the terminal
            os.writeBytes("exit\n");
            os.flush();
// read ping replys
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ttl")) {
                    ItemPing itemPing = new ItemPing();
                    itemPing.setResult(line);
                    String[] lss = line.split(" ");
                    for (String x : lss) {
                        if (x.contains("time=")) {
                            itemPing.setTime(x);
                        }
                        if (x.contains("icmp_seq")) {
                            itemPing.setSeq(x);
                        }
                        if (x.contains(":")) {
                            itemPing.setIp(x);
                        }
                    }
                    Log.e("line", line);
                    Log.e("tostrin", itemPing.toString());
                    sub.onNext(itemPing);
                }
            }
        });
        getData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(itemPing -> listviewAdapter.addPing(itemPing))
                .subscribe();
```
