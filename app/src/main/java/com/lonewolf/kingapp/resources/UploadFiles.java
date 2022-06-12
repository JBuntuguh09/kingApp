package com.lonewolf.kingapp.resources;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;



import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class UploadFiles {

//    private final Executor mExecutor = Executors.newSingleThreadExecutor();
//    private Drive mDriveService;
//
//    public UploadFiles(Drive mDriveService) {
//        this.mDriveService = mDriveService;
//    }
//
//    public Task<String> createFile(String filepath){
//        return Tasks.call(mExecutor, ()-> {
//            File filemeta = new File();
//            filemeta.setName("");
//            java.io.File file = new java.io.File(filepath);
//
//            FileContent mediacontent = new FileContent("", file);
//            File myfile = null;
//            try {
//                myfile = mDriveService.files().create(filemeta, mediacontent).execute();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//            if(myfile==null){
//                throw new IOException("Null result");
//            }
//
//            return myfile.getId();
//        });
//    }
}
