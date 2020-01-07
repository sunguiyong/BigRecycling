package ceshi.handover.scinan.com.huishoubaobigrecycling.utils.download;

public interface Contract {
    interface View
    {
        void showError(String s);
//        void showUpdate(UpdateInfo updateInfo);
        void downLoading(int i);
        void downSuccess();
        void downFial();
        void setMax(long l);
    }

    interface Presenter{
        void getApkInfo();
        void downFile(String url);
    }
}
