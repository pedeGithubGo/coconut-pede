package app.beelabs.com.coconut.presenter;

import java.io.File;

import app.beelabs.com.coconut.model.api.response.ProfileResponseModel;
import app.beelabs.com.coconut.model.api.response.SourceResponse;
import app.beelabs.com.coconut.model.api.response.SummaryResponse;
import app.beelabs.com.coconut.model.dao.ResourceDao;
import app.beelabs.com.coconut.ui.IMainView;
import app.beelabs.com.coconut.ui.ISecondView;
import app.beelabs.com.coconut.ui.fragment.IMainFragmentView;
import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.IView;
import app.beelabs.com.codebase.base.response.BaseResponse;
import app.beelabs.com.codebase.support.rx.RxObserver;
import io.reactivex.disposables.Disposable;

public class ResourcePresenter extends BasePresenter implements ResourceDao.IResourceDao {

    private IView iv;

    private IMainView imv;
    private ISecondView isv;

    private IMainFragmentView ifv;

    /**
     * Mandatory
     **/
    public ResourcePresenter(IView iv) {
        this.iv = iv;
        this.imv = (iv instanceof IMainView) ? (IMainView) iv : imv;
        this.ifv = (iv instanceof IMainFragmentView) ? (IMainFragmentView) iv : ifv;
        this.isv = (iv instanceof ISecondView) ? (ISecondView) iv : isv;
    }


    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @Override
    public void postPhoneNumber(String phone) {
        new ResourceDao(this).postPhoneNumber(phone)
                .subscribe(new RxObserver<SummaryResponse>(ifv, "Ambil Data Summary...", 10000) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);

                    }

                    @Override
                    public void onNext(Object o) {
                        super.onNext(o);
                        ifv.handleDataSummary((SummaryResponse) o);
                    }
                });

    }


//    @Override
//    public void getSourceRX(String messageLoading) {
//        new ResourceDao(this).getSourceRXDAO()
//                .subscribe(new RxObserver<SourceResponse>(isv, null, 500){
//                    @Override
//                    public void onNext(Object o) {
//                        super.onNext(o); // Mandatory
//                        isv.handleDataSource((SourceResponse) o);
//                    }
//                });
//    }

    @Override
    public void getProfileRX() {
        new ResourceDao(this).getProfileRxDAO()
                .subscribe(new RxObserver<ProfileResponseModel>(imv, null, 500) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d); // Mandatory
                        imv.handleProcessing();
                    }

                    @Override
                    public void onNext(Object o) {
                        super.onNext(o); // Mandatory
                        imv.handleProfileDone((ProfileResponseModel) o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e); // Mandatory
                        imv.handleError(e.getMessage());
                    }

                });

    }

    @Override
    public void getSource() {
        new ResourceDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                SourceResponse model = (SourceResponse) br;
                imv.handleDataSource(model);
            }
        }).getSourceDAO();
    }

    @Override
    public void getSourceRX(String messageLoading) {
        new ResourceDao(this).getSourceRXDAO()
                .subscribe(new RxObserver<ProfileResponseModel>(iv, messageLoading) {


                    @Override
                    public void onNext(IView iv, Object o) {
                        if (iv instanceof IMainView)
                            ((IMainView) iv).handleDataSource((SourceResponse) o);
                        else if (iv instanceof ISecondView)
                            ((ISecondView) iv).handleDataSource((SourceResponse) o);
                    }
                });
    }

    @Override
    public void uploadFile(String noteVal, String startTimeVal, String endTimeVal,
                           String startDateVal, String endDateVal,
                           String employeeIdVal, File file) {

        new ResourceDao(this, new OnPresenterResponseCallback() {
            @Override
            public void call(BaseResponse br) {
                BaseResponse model = br;
                imv.handleDataUpload(model);
            }
        }).postingUploadFile(noteVal, startTimeVal, endTimeVal,
                startDateVal, endDateVal, employeeIdVal, file);
    }
}
