package com.andy.auth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.*;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends FragmentActivity
{
    private final boolean USE_CONTEXT = false;

    private String[] mAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAlbums = getResources().getStringArray(R.array.rush_albums);

        getSupportFragmentManager().beginTransaction()
            .add(R.id.menu_container, getListFragment())
            .commit();
    }

    public DialogFragment getDialogFragment(final String title) {
        return new DialogFragment() {
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
            }
        };
    }

    public ListFragment getListFragment() {
        return new ListFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setListAdapter(new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_list_item_1, mAlbums
                ));
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                if (USE_CONTEXT) {
                    registerForContextMenu(getListView());
                } else {
                    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                    getListView().setMultiChoiceModeListener(getListener());
                }
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                super.onCreateContextMenu(menu, v, menuInfo);
                getMenuInflater().inflate(R.menu.menu_context_album, menu);
            }

            @Override
            public boolean onContextItemSelected(MenuItem item) {
                getDialogFragment(item.getTitle().toString()).show(getSupportFragmentManager(), "alert");
                return super.onContextItemSelected(item);
            }
        };
    }

    public AbsListView.MultiChoiceModeListener getListener() {
        return new AbsListView.MultiChoiceModeListener() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_context_album, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                getDialogFragment(item.getTitle().toString()).show(getSupportFragmentManager(), "alert");
                return true;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        };
    }

}
