package ru.eupavlov.btms.mvp.file

import com.arellomobile.mvp.InjectViewState
import org.koin.standalone.inject
import ru.eupavlov.btms.common.BasePresenter
import ru.eupavlov.btms.model.Session
import java.io.File

@InjectViewState
class FilePresenter : BasePresenter<FileView>() {
    private val session: Session by inject()

    fun selectFile(byteArray: ByteArray) {
        session.selectedFile = byteArray
    //    viewState.setFileName(file.name)
    }

    fun getSelectedFile(): ByteArray? {
        return session.selectedFile
    }
}