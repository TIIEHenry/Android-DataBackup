package com.xayah.databackup.fragment.backup

import android.content.Intent
import android.view.View
import androidx.appcompat.widget.ListPopupWindow
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.xayah.databackup.App
import com.xayah.databackup.activity.list.AppListActivity
import com.xayah.databackup.activity.processing.ProcessingActivity
import com.xayah.databackup.util.Bashrc
import com.xayah.databackup.util.Command
import com.xayah.databackup.util.readBackupUser
import com.xayah.databackup.util.saveBackupUser
import com.xayah.design.adapter.PopupListAdapter
import com.xayah.design.util.getPixels
import com.xayah.design.util.measureWidth

class BackupViewModel : ViewModel() {
    var backupUser = ObservableField(App.globalContext.readBackupUser())
    var appNum = ObservableField("0")
    var dataNum = ObservableField("0")

    fun onChangeUser(v: View) {
        val context = v.context
        val items =
            if (Bashrc.listUsers().first) Bashrc.listUsers().second.toTypedArray() else arrayOf("0")
        val choice = items.indexOf(App.globalContext.readBackupUser())

        ListPopupWindow(context).apply {
            val adapter = PopupListAdapter(
                context,
                items.toList(),
                choice,
            )
            setAdapter(adapter)
            anchorView = v
            width = adapter.measureWidth(context)
                .coerceAtLeast(context.getPixels(com.xayah.design.R.dimen.dialog_menu_min_width))
            isModal = true
            horizontalOffset =
                context.getPixels(com.xayah.design.R.dimen.item_header_component_size) + context.getPixels(
                    com.xayah.design.R.dimen.item_header_margin
                ) * 2
            setOnItemClickListener { _, _, position, _ ->
                context.saveBackupUser(items[position])
                backupUser.set(items[position])
                dismiss()
            }
            show()
        }
    }

    fun onSelectAppBtnClick(v: View) {
        v.context.startActivity(Intent(v.context, AppListActivity::class.java).apply {
            putExtra("isRestore", false)
        })
    }

    fun onBackupAppBtnClick(v: View) {
        v.context.startActivity(Intent(v.context, ProcessingActivity::class.java).apply {
            putExtra("isRestore", false)
            putExtra("isMedia", false)
        })
    }

    fun onBackupMediaBtnClick(v: View) {
        v.context.startActivity(
            Intent(v.context, ProcessingActivity::class.java).apply {
                putExtra("isRestore", false)
                putExtra("isMedia", true)
            }
        )
    }

    private fun setNum() {
        val that = this
        Command.getCachedAppInfoBackupListNum().apply {
            that.appNum.set(this.appNum.toString())
            that.dataNum.set(this.dataNum.toString())
        }
    }

    private fun refresh() {
        setNum()
    }

    fun initialize() {
        refresh()
    }
}