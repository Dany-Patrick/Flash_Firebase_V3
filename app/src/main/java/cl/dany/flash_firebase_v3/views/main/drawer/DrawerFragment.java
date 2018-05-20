package cl.dany.flash_firebase_v3.views.main.drawer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import cl.dany.flash_firebase_v3.R;
import cl.dany.flash_firebase_v3.data.CurrentUser;
import cl.dany.flash_firebase_v3.views.login.LoginActivity;


public class DrawerFragment extends Fragment implements PhotoCallback{

    private MagicalPermissions magicalPermissions;
    private int RESIZE_PHOTO_PIXELS_PERCENTAGE = 30;
    private MagicalCamera magicalCamera;
    private CircularImageView avatar ;
    public DrawerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.logoutTv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
            }
        });
        TextView textView1 = view.findViewById(R.id.emailTv);
        textView1.setText(new CurrentUser().email());

       // imageView = view.findViewById(R.id.photoIv);
        //array de permisos
        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(getActivity(),RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);
        avatar = view.findViewById(R.id.avatarCI);
        new PhotoValidation(getContext(),this).validate();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        magicalPermissions.permissionResult(requestCode, permissions, grantResults);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //CALL THIS METHOD EVER
        magicalCamera.resultPhoto(requestCode, resultCode, data);
        if(Activity.RESULT_OK == resultCode)
        {
            Bitmap photo = magicalCamera.getPhoto();
            //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);

            //with this form you obtain the bitmap (in this example set this bitmap in image view)
            //imageView.setImageBitmap(magicalCamera.getPhoto());

            String path = magicalCamera.savePhotoInMemoryDevice(photo,"myPhotoName","myDirectoryName", MagicalCamera.JPEG, true);
            path = "file://"+path;
            setPhoto(path);
            new UploadPhoto(getContext()).toFirebase(path);
            if(path != null){
               // imageView.setImageBitmap(photo);
                Toast.makeText(getContext(), "The photo is photoSave in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
                //uploadPhoto(path);
            }else{
               requestSelfie();
            }
        }

    }
    private void requestSelfie()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Selfie")
                .setMessage("Para completar el registro debes tener una selfie actualizada.")
                .setCancelable(false)
                .setPositiveButton("Selfie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        magicalCamera.takeFragmentPhoto(DrawerFragment.this);
                        dialog.dismiss();
                    }
                }).show();

    }
    private void setPhoto(String url)
    {
        Picasso.get().load(url).centerCrop().fit().into(avatar);
    }

    @Override
    public void emptyPhoto() {
        requestSelfie();
    }

    @Override
    public void photoAvailiable(String url) {
        setPhoto(url);
    }
}
