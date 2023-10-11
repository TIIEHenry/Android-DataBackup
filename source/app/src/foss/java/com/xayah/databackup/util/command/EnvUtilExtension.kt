package com.xayah.databackup.util.command

import android.content.Context
import com.xayah.librootservice.util.withIOContext

suspend fun releaseBin(context: Context): Boolean = withIOContext {
    var result = true
    if (EnvUtil.releaseBase(context).not()) result = false
    result
}
