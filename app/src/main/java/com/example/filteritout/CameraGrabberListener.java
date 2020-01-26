package com.example.filteritout;

public interface CameraGrabberListener {
    void onCameraInitialized();
    void onCameraError(String errorMsg);
}