package app.beelabs.com.codebase.component.dialog

import android.app.Activity
import android.os.Bundle
import app.beelabs.com.codebase.R
import app.beelabs.com.codebase.base.BaseDialog
import app.beelabs.com.codebase.base.contract.IView
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.dialog_alert_network_noconnection.*

class AlertNetworkNoConnectionDialog(private val iview: IView, style: Int) : BaseDialog(iview as Activity, style) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowContentDialogLayout(R.layout.dialog_alert_network_noconnection)

        close_dialog.setOnClickListener {
            dismiss()
            iview.handleRetryConnection();
        }
    }

}