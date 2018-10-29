package ru.eupavlov.btms.mvp.file

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.provider.DocumentFile
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.file_fragment.*
import ru.eupavlov.btms.CHOOSE_FILE
import ru.eupavlov.btms.R
import ru.eupavlov.btms.TRANSFER_FRAGMENT
import ru.eupavlov.btms.common.BaseFragment
import java.io.File
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.content.ContentResolver
import android.net.Uri
import ru.eupavlov.btms.showOrHide
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class FileFragment : BaseFragment(), FileView {


    @InjectPresenter
    lateinit var filePresenter: FilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.file_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choose_file.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), CHOOSE_FILE)
        }
    }

    override fun onResume() {
        super.onResume()
        filename.showOrHide(filePresenter.getSelectedFile() != null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOOSE_FILE && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            selectedImage?.path?.let { it ->
                Log.e(TAG, "selected file: ${selectedImage.path}")
                convertImageToByte(selectedImage)?.let { ba ->
                    filePresenter.selectFile(ba)
                }
                baseActivity.changeFragment(TRANSFER_FRAGMENT)
            }
        }
    }

    fun convertImageToByte(uri: Uri): ByteArray? {
        var data: ByteArray? = null
        try {
            val cr = baseActivity.contentResolver
            val inputStream = cr.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            data = baos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return data
    }


}