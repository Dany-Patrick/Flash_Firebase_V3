package cl.dany.flash_firebase_v3.Views.Main.finder;

public interface FinderCallback {
    void error(String error);
    void success();
    void notFound();
}
