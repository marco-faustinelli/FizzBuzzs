package org.faustinelli.android.fizzbuzzs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.onebip.fizzbuzz.InsertNumberActivity;

import org.faustinelli.android.fizzbuzzs.model.FBGame;
import org.faustinelli.android.fizzbuzzs.model.FBGames;

import java.util.ArrayList;

/**
 * Created by muzietto on 20/06/15.
 */
public class FBGamesListFragment extends ListFragment {
    private ArrayList<FBGame> mGames;
    private static final String TAG = "FBGamesListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle("FizzBuzz Games");

        // invoke the singleton
        mGames = FBGames.get(getActivity()).getGames();

        FBGameAdapter adapter = new FBGameAdapter(mGames);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        registerForContextMenu(listView);
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        FBGame game = ((FBGameAdapter) getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), InsertNumberActivity.class);
        i.putExtra(InsertNumberActivity.EXTRA_INPUT, game.getNumber());
        startActivity(i); // why not startActivityForResult ????
    }

    @Override
    public void onResume() {
        super.onResume();
        ((FBGameAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_games_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_game:
                FBGame newGame = new FBGame();
                FBGames.get(getActivity()).addGame(newGame);

                Intent i = new Intent(getActivity(), InsertNumberActivity.class);
                i.putExtra(FBGame.EXTRA_GAME_ID, newGame.getId());
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.game_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        FBGameAdapter adapter = (FBGameAdapter) getListAdapter();
        FBGame game = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_game:
                FBGames.get(getActivity()).deleteGame(game);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private class FBGameAdapter extends ArrayAdapter<FBGame> {

        public FBGameAdapter(ArrayList<FBGame> games) {
            super(getActivity(), 0, games);
        }

        @Override
        public View getView(int position, View listItemView, ViewGroup parent) {

            if (listItemView == null) {
                listItemView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_game, null);
            }

            FBGame game = getItem(position);

            TextView idTextView = (TextView)listItemView.findViewById(R.id.list_item_id);
            idTextView.setText(game.getId().toString());

            TextView numberTextView = (TextView)listItemView.findViewById(R.id.list_item_number);
            numberTextView.setText(game.getNumber());

            return listItemView;
        }
    }
}
