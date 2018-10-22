package app.beelabs.com.coconut.model.dao;

import app.beelabs.com.coconut.model.api.Api;
import app.beelabs.com.coconut.model.api.response.SourceResponse;
import app.beelabs.com.coconut.presenter.ResourcePresenter;
import app.beelabs.com.codebase.base.BaseDao;
import app.beelabs.com.codebase.base.BasePresenter;
import app.beelabs.com.codebase.base.IPresenter;
import app.beelabs.com.codebase.base.response.BaseResponse;
import retrofit2.Response;

import static app.beelabs.com.coconut.IConfig.KEY_CALLER_API_SOURCE;

/**
 * Created by arysuryawan on 8/19/17.
 */

public class ResourceDao extends BaseDao {

    private ResourcePresenter.OnPresenterResponseCallback onPresenterResponseCallback;
    private IResourceDao rdao;

    private BasePresenter bp;

    // definition usecase
    public interface IResourceDao  {
        void postPhoneNumber(String phone);

        BasePresenter getSource();


    }


    public ResourceDao(IResourceDao rdao, ResourcePresenter.OnPresenterResponseCallback onPresenterResponseCallback) {
        this.rdao = rdao;
        this.onPresenterResponseCallback = onPresenterResponseCallback;
    }

    public void getSource(BasePresenter bp) {
        this.bp = bp;
        Api.doApiSources(BaseDao.getInstance(this, KEY_CALLER_API_SOURCE).callback);
    }

//    public void getArticleDAO(IPresenter iView, Callback callback) {
//        Api.doApiArticles(iView.getBaseActivity(), callback);
//    }

    public void postPhoneNumber(String phone) {
//        Api.doTestFin(phone, BaseDao.getInstance(this, KEY_CALLER_API_SUMMARY).callback);
    }


    @Override
    public void onApiResponseCallback(BaseResponse br, int responseCode, Response response) {
        if (response.isSuccessful()) {
            if (responseCode == KEY_CALLER_API_SOURCE) {
                SourceResponse model = (SourceResponse) br;
                if (model.getStatus().equals("ok")) {
                    onPresenterResponseCallback.call(model);
                }
            }
        }
    }
}
