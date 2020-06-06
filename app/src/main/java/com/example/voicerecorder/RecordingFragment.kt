package com.example.voicerecorder

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recording.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class RecordingFragment : Fragment() {

    private val recordPermission: String = Manifest.permission.RECORD_AUDIO
    private val PERMISSION_CODE = 23
    private var isRecording = false
    private var mediaRecorder: MediaRecorder? = null
    lateinit var recordFile: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recording, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recordBtn.setOnClickListener {
            Log.i("get started", "button is taped")
            if (isRecording) {
                stopRecording()
                isRecording = false
                Log.i("get stopped", "get stopped")
            } else {
                if (checkPermission()) {
                    startRecording()
                    isRecording = true
                    Log.i("get started", "$isRecording")
                } else Log.i("get started", "мимо")
            }
        }
    }

    private fun startRecording(
    ) {
        record_timer.setBase(SystemClock.elapsedRealtime())
        record_timer.start()
        val recordPath = getActivity()?.getExternalFilesDir("/")?.absolutePath
        val formatter = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA)
        val now = Date()


        recordFile = "Recording" + formatter.format(now) + ".3gp"

        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setOutputFile(recordPath + "/" + recordFile)
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mediaRecorder?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaRecorder?.start()
        Log.i("get started", "get started")
    }

    private fun stopRecording() {
        record_timer.stop()
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder?.release()
        mediaRecorder = null

    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                recordPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(recordPermission),
                PERMISSION_CODE
            )
            return true
            //  }

        } else return true
    }
}

