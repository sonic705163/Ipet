package idv.randy.ut;

public interface AsyncListener {
    void onError();
    void onGoing(int progress);
    void onFinish(String result);
}
