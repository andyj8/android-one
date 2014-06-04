package com.andy.auth;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

public class MenuActivity extends FragmentActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final String[] albums = getResources().getStringArray(R.array.rush_albums);

        Fragment fragment = new ListFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setListAdapter(new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_list_item_1, albums
                ));
            }
            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
                registerForContextMenu(getListView());
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

        getSupportFragmentManager().beginTransaction()
            .add(R.id.menu_container, fragment)
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

}
