package com.pallav.feedbacknative;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pallav.feedbacknative.Adapter.EmpListAdapter;
import com.pallav.feedbacknative.Adapter.FeedbackListAdapter;
import com.pallav.feedbacknative.Util.NetworkUtil;
import com.pallav.feedbacknative.Util.Services;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeesActivity extends AppCompatActivity implements Services.webserviceAsync, SearchView.OnQueryTextListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        //callWebServiceForGetEmployeesData();
        URL url = NetworkUtil.buildURL(getResources().getString(R.string.base_SOAP)+ "TokenTest2AU?Email="
                + new SetSharedPreferences().getValue(EmployeesActivity.this, "Username")
                + "&Token=af9bce267343ad72bd6abe7aff58edf2");
        AsyncCallGetEMPList  task = new AsyncCallGetEMPList();
        task.execute(url);

        setUpViews();
    }

    Services services;
    private void callWebServiceForGetEmployeesData() {
        services = new Services(EmployeesActivity.this, null);
        services.isShowProgress(true);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                JSONObject header = new JSONObject();
                try {
                    header.put("","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                services.sendRequestWithArrayRequest(getResources().getString(R.string.base_url) + getResources().getString(R.string.employees)
                        , null, header, null, Services.webcallid.GET_EMP_LIST);
            }
        }, 300);
    }

    private RecyclerView recyclerView;
    private EmpListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView tbMainSearch;


    ArrayList<HashMap<String, String>> arrData = new ArrayList<>();

    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mAdapter = new EmpListAdapter(EmployeesActivity.this, arrData);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void getResponse(String url, JSONObject result, Object status, Services.webcallid callId) {

    }

    @Override
    public void getResponseWithJsonArray(String url, JSONArray result, Object status, Services.webcallid callId) {
        Log.e("result", ""+result);

        if (callId == Services.webcallid.GET_EMP_LIST && result != null) {

            JSONArray jsonArray = result;

            for (int i = 0; i < jsonArray.length(); i++) {
                HashMap<String, String> map = new HashMap<>();
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    map.put("name", jsonObject.get("employee_name").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrData.add(map);
            }
            setRecyclerView();

        }
    }

    class AsyncCallGetEMPList extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            //Call Web Method
            //  GetApi.invokeJSONWS("GetFeedBackDetailNew");
            String data = null;

            try {
                data = NetworkUtil.getResponse(urls[0]);
                Log.d("Magaj ni patar fadai gayi", " "+data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("data", data);
            return data;
        }

        @Override
        protected void onPostExecute(String result) {

            try{

                JSONArray jsonArray = new JSONArray(result);
                arrData = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        map.put("name", jsonObject.get("FirstName").toString() + " " + jsonObject.get("LastName").toString());
                        map.put("FirstName", jsonObject.get("FirstName").toString());
                        map.put("LastName", jsonObject.get("LastName").toString());
                        map.put("Email", jsonObject.get("Email").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    arrData.add(map);
                }
                setRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }




        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {
            super.onPreExecute();
            //  webservicePG.setVisibility(View.VISIBLE);
            /*loginStatus = LoginWebservice.invokeLoginWS(editTextUsername,editTextPassword,"getLogin");*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }

    private void setUpViews() {
        tbMainSearch = (SearchView)findViewById(R.id.searchView);
        tbMainSearch.setOnQueryTextListener(this);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchmenuItem = menu.findItem(R.id.menu_toolbarsearch);
        SearchView searchView = (SearchView) mSearchmenuItem.getActionView();
        searchView.setQueryHint("enter Text");
        searchView.setOnQueryTextListener(this  );
        Log.d("TAG", "onCreateOptionsMenu: mSearchmenuItem->" + mSearchmenuItem.getActionView());
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

       mAdapter.getFilter().filter(s);
        return true;

    }
}
