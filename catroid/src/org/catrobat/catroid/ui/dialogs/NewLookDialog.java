/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.ui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.PointToBrick.SpinnerAdapterWrapper;
import org.catrobat.catroid.io.StorageHandler;
import org.catrobat.catroid.ui.ProgramMenuActivity;
import org.catrobat.catroid.ui.ScriptActivity;
import org.catrobat.catroid.ui.controller.LookController;
import org.catrobat.catroid.ui.fragment.LookFragment;
import org.catrobat.catroid.utils.UtilCamera;
import org.catrobat.catroid.utils.Utils;

import java.io.File;
import java.io.IOException;

public class NewLookDialog extends DialogFragment {

    public static final String DIALOG_FRAGMENT_TAG = "dialog_new_look";

    private static final int REQUEST_SELECT_IMAGE = 0;
    private static final int REQUEST_CREATE_POCKET_PAINT_IMAGE = 1;
    private static final int REQUEST_TAKE_PICTURE = 2;

    private View dialogView;
    private LookFragment lookFragment = null;


    public void showDialog(LookFragment fragment) {
        lookFragment = fragment;
        show(lookFragment.getActivity().getSupportFragmentManager(), DIALOG_FRAGMENT_TAG);
    }


    public static NewLookDialog newInstance()
    {
       return new NewLookDialog();

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_new_look, null);
        setupPaintroidButton(dialogView);
        setupGalleryButton(dialogView);
        setupCameraButton(dialogView);

        AlertDialog dialog = null;
        AlertDialog.Builder dialogBuilder = new CustomAlertDialogBuilder(getActivity()).setView(dialogView).setTitle(
                R.string.new_look_dialog_title);

            dialog = createDialog(dialogBuilder);


       dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private AlertDialog createDialog(AlertDialog.Builder dialogBuilder) {
        AlertDialog dialog = dialogBuilder.create();
        return dialog;
    }


    private void setupPaintroidButton(View parentView) {
        View paintroidButton = parentView.findViewById(R.id.dialog_new_look_paintroid);

        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(Constants.POCKET_PAINT_PACKAGE_NAME,
                Constants.POCKET_PAINT_INTENT_ACTIVITY_NAME));
        if (LookController.getInstance().checkIfPocketPaintIsInstalled(intent, getActivity())) {
            paintroidButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    lookFragment.addLookDrawNewImage();
                    NewLookDialog.this.dismiss();
                }
            });
        } else {
            LinearLayout linearLayout = (LinearLayout) parentView.findViewById(R.id.dialog_new_look_chooser_layout);
            paintroidButton.setVisibility(View.GONE);
            linearLayout.setWeightSum(2f);
        }
    }

    private void setupGalleryButton(View parentView) {
        View galleryButton = parentView.findViewById(R.id.dialog_new_look_gallery);

        galleryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lookFragment.addLookChooseImage();
                NewLookDialog.this.dismiss();
            }
        });
    }

    private void setupCameraButton(View parentView) {
        View cameraButton = parentView.findViewById(R.id.dialog_new_look_camera);

        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                lookFragment.addLookFromCamera();
                NewLookDialog.this.dismiss();
            }
        });
    }


}
