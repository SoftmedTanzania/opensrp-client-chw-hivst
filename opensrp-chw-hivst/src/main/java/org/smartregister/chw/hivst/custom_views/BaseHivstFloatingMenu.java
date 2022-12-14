package org.smartregister.chw.hivst.custom_views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.smartregister.chw.hivst.domain.MemberObject;
import org.smartregister.chw.hivst.fragment.BaseHivstCallDialogFragment;
import org.smartregister.chw.hivst.listener.OnClickFloatingMenu;
import org.smartregister.hivst.R;

public class BaseHivstFloatingMenu extends LinearLayout implements View.OnClickListener {

    public FloatingActionButton fab;
    private Animation fabOpen;
    private Animation fabClose;
    private Animation rotateForward;
    private Animation rotateBack;
    private View callLayout;
    protected RelativeLayout referLayout;
    private RelativeLayout activityMain;
    private boolean isFabMenuOpen = false;
    private LinearLayout menuBar;
    private OnClickFloatingMenu onClickFloatingMenu;
    private MemberObject MEMBER_OBJECT;

    public BaseHivstFloatingMenu(Context context, MemberObject MEMBER_OBJECT) {
        super(context);
        initUi();
        this.MEMBER_OBJECT = MEMBER_OBJECT;
    }

    public void initUi() {
        inflate(getContext(), R.layout.view_hivst_floating_menu, this);
        fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
        rotateBack = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_back);

        activityMain = findViewById(R.id.activity_main);
        menuBar = findViewById(R.id.menu_bar);

        fab = findViewById(R.id.hivst_fab);
        fab.setOnClickListener(this);

        callLayout = findViewById(R.id.call_layout);
        callLayout.setOnClickListener(this);
        callLayout.setClickable(false);

        referLayout = findViewById(R.id.refer_to_facility_layout);
        referLayout.setOnClickListener(this);
        referLayout.setClickable(false);

        menuBar.setVisibility(GONE);
    }

    public void setFloatMenuClickListener(OnClickFloatingMenu onClickFloatingMenu) {
        this.onClickFloatingMenu = onClickFloatingMenu;
    }

    @Override
    public void onClick(View view) {
        onClickFloatingMenu.onClickMenu(view.getId());
    }

    public void animateFAB() {
        if (menuBar.getVisibility() == GONE) {
            menuBar.setVisibility(VISIBLE);
        }

        if (isFabMenuOpen) {
            activityMain.setBackgroundResource(R.color.transparent);
            fab.startAnimation(rotateBack);
            fab.setImageResource(R.drawable.ic_edit_white);

            callLayout.startAnimation(fabClose);
            callLayout.setClickable(false);

            referLayout.startAnimation(fabClose);
            referLayout.setClickable(false);
            isFabMenuOpen = false;
        } else {
            activityMain.setBackgroundResource(R.color.grey_tranparent_50);
            fab.startAnimation(rotateForward);
            fab.setImageResource(R.drawable.ic_baseline_add_24);

            callLayout.startAnimation(fabOpen);
            callLayout.setClickable(true);

            referLayout.startAnimation(fabOpen);
            referLayout.setClickable(true);
            isFabMenuOpen = true;
        }
    }

    public void launchCallWidget() {
        BaseHivstCallDialogFragment.launchDialog((Activity) this.getContext(), MEMBER_OBJECT);
    }

    public View getCallLayout() {
        return callLayout;
    }

    public static void redrawWithOption(LinearLayout menu, boolean has_phone) {
        TextView callTextView = menu.findViewById(R.id.CallTextView);
        TextView callTextViewHint = menu.findViewById(R.id.CallTextViewHint);
        setCallLayoutListener(has_phone, menu);
        if (has_phone) {
            callTextViewHint.setVisibility(GONE);
            callTextView.setTypeface(null, Typeface.NORMAL);
            callTextView.setTextColor(menu.getResources().getColor(android.R.color.black));
            ((FloatingActionButton) menu.findViewById(R.id.callFab)).getDrawable().setAlpha(255);

        } else {
            callTextViewHint.setVisibility(VISIBLE);
            callTextView.setTypeface(null, Typeface.ITALIC);
            callTextView.setTextColor(menu.getResources().getColor(R.color.grey));
            ((FloatingActionButton) menu.findViewById(R.id.callFab)).getDrawable().setAlpha(122);
        }

    }

    private static void setCallLayoutListener(boolean has_phone, LinearLayout menu) {
        BaseHivstFloatingMenu hivstFloatingMenu;
        if (has_phone && menu instanceof BaseHivstFloatingMenu) {
            hivstFloatingMenu = (BaseHivstFloatingMenu) menu;
            hivstFloatingMenu.getCallLayout().setOnClickListener(hivstFloatingMenu);
        } else if (!has_phone && menu instanceof BaseHivstFloatingMenu) {
            hivstFloatingMenu = (BaseHivstFloatingMenu) menu;
            hivstFloatingMenu.getCallLayout().setOnClickListener(null);
        }
    }
}