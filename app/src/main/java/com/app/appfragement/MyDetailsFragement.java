package com.app.appfragement;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.Utility.Utility;
import com.app.aggro.R;
import com.app.aggro.Registration;
import com.app.spinneradapter.NothingSelectedSpinnerAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyDetailsFragement.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyDetailsFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDetailsFragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Edit Text and spinner varibale used to update the
     * Account
     *
     */

    private EditText name_ed, age_ed, location_ed, email_ed;
    private Spinner gender_sp;
    private TextView name_tv, age_tv, gender_tv, location_tv, email_tv;
    private ImageView img_name, img_age, img_gender, img_location, img_email;
    private Button btn_update;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyDetailsFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDetailsFragement newInstance(String param1, String param2) {
        MyDetailsFragement fragment = new MyDetailsFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyDetailsFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_my_details_fragement, container, false);
        init(rootView);
        setFontOnViewLoaded();

        makeDeactivate(name_ed);
        makeDeactivate(age_ed);
        makeDeactivate(location_ed);
        makeDeactivate(email_ed);
        makeSpinnerDeactivate();

        setDummyDataOnLoad();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(android.view.Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchAction = menu.findItem(R.id.action_search);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_search:
                handleMenuSearch();
                return true;
        }

        return false;
    }

    protected void handleMenuSearch(){
        ActionBar action = ((AppCompatActivity)getActivity()).getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity)getActivity()).getCurrentFocus().getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_open_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_action));

            isSearchOpened = true;
        }
    }

    private void doSearch() {
        Toast.makeText(getActivity(), "LENGTH", Toast.LENGTH_LONG).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void init(View root){
        name_ed = (EditText)root.findViewById(R.id.name_ed);
        age_ed = (EditText)root.findViewById(R.id.age_ed);
        location_ed = (EditText)root.findViewById(R.id.location_ed);
        email_ed = (EditText)root.findViewById(R.id.email_ed);
        gender_sp = (Spinner)root.findViewById(R.id.gender_sp);
        btn_update = (Button)root.findViewById(R.id.update);

        name_tv = (TextView)root.findViewById(R.id.hi);
        age_tv = (TextView)root.findViewById(R.id.age);
        gender_tv = (TextView)root.findViewById(R.id.gender);
        location_tv = (TextView)root.findViewById(R.id.location);
        email_tv = (TextView)root.findViewById(R.id.email);

        img_name = (ImageView)root.findViewById(R.id.imageView_name);
        img_age = (ImageView)root.findViewById(R.id.imageView_age);
        img_gender = (ImageView)root.findViewById(R.id.imageView_gender);
        img_location = (ImageView)root.findViewById(R.id.imageView_location);
        img_email = (ImageView)root.findViewById(R.id.imageView_email);

        img_name.setOnClickListener(clickListener);
        img_age.setOnClickListener(clickListener);
        img_gender.setOnClickListener(clickListener);
        img_location.setOnClickListener(clickListener);
        img_email.setOnClickListener(clickListener);
        btn_update.setOnClickListener(clickListener);


    }

    private void setFontOnViewLoaded(){
        name_ed.setTypeface(Registration.mpRegular);
        age_ed.setTypeface(Registration.mpRegular);
        location_ed.setTypeface(Registration.mpRegular);
        email_ed.setTypeface(Registration.mpRegular);

        name_tv.setTypeface(Registration.mpRegular);
        age_tv.setTypeface(Registration.mpRegular);
        location_tv.setTypeface(Registration.mpRegular);
        email_tv.setTypeface(Registration.mpRegular);
    }

    private void setDummyDataOnLoad(){
        name_ed.setText(Utility.readUserInfoFromPrefs(getActivity(),getString(R.string.username)));
        age_ed.setText(Utility.readUserInfoFromPrefs(getActivity(),getString(R.string.age)));
        location_ed.setText(Utility.readUserInfoFromPrefs(getActivity(), getString(R.string.location)));
        email_ed.setText(Utility.readUserInfoFromPrefs(getActivity(), getString(R.string.email)));
        makeSpinnerActivate();
        if (Utility.readUserInfoFromPrefs(getActivity(),getString(R.string.gender)).equals("M"))
            gender_sp.setSelection(0);
        else
            gender_sp.setSelection(1);
        gender_sp.setEnabled(false);
    }

    private void makeActive(EditText ed){
        ed.setEnabled(true);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);
    }

    private void makeDeactivate(EditText ed){
        ed.setEnabled(false);
    }

    private void makeSpinnerActivate(){
        gender_sp.setEnabled(true);
        addItemToSpinner();
        addListenerToSpinner();
    }
    private void makeSpinnerDeactivate(){
        gender_sp.setEnabled(false);
    }

    private void addItemToSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_aray, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_sp.setPrompt(getResources().getString(R.string.spinner_prompt));

        gender_sp.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.contact_spinner_row_nothing_selected, getActivity()));
    }

    private void addListenerToSpinner(){
        gender_sp.setOnItemSelectedListener(new SystemSpinner());
    }

    private void selectItem(int item) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        if (item == 1){
            fragment = com.app.appfragement.Menu.newInstance("", item);
        }else if (item == 2){
            fragment = com.app.appfragement.Menu.newInstance("", item);
        }
        else {
            fragment = com.app.appfragement.Menu.newInstance("", item);
        }
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    /**
     * An inner class that will that handle
     * Spinner Selection event
     *
     */

    public class SystemSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            gender_sp.setSelection(position);
            switch (position) {
                case 0:
                    //Male Selection
                    break;
                case 1:
                    //Female Selection
                    break;
                default:
                    break;
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           switch (v.getId()){
               case R.id.imageView_name:
                   //Edit Name
                   makeActive(name_ed);
                   break;
               case R.id.imageView_age:
                   //Edit age
                   makeActive(age_ed);
                   break;
               case R.id.imageView_gender:
                   //Edit gender
                   makeSpinnerActivate();
                   break;
               case R.id.imageView_location:
                   //Edit location
                   makeActive(location_ed);
                   break;
               case R.id.imageView_email:
                   //Edit email
                   makeActive(email_ed);
                   break;

               case R.id.update:
                   //Edit email
                 selectItem(0);
                   break;

               default:
                   break;

           }
        }
    };

}
