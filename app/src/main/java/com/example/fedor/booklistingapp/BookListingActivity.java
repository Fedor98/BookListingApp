/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.fedor.booklistingapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListingActivity extends AppCompatActivity
        implements LoaderCallbacks<List<BookListing>> {

    private static final String LOG_TAG = BookListingActivity.class.getName();

    /**
     * URL for book data from the Google Books dataset
     */
    private static final String GB_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";

    private static final String GB_REQUEST_URL_MR = "&maxResults=10";

    private String query = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private int booklisting_loader_id = 1;

    /**
     * Adapter for the list of books
     */
    private BookListingAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklisting_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListingListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListingListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookListingAdapter(this, new ArrayList<BookListing>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListingListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        bookListingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current book that was clicked on
                BookListing currentBookListing = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookListingUri = Uri.parse(currentBookListing.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookListingUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Kick of the initial query for "android"
        kickQuery();

        // Find a reference to the {@link Button} in the layout
        Button searchButton = (Button) findViewById(R.id.search_button);

        // Set an item click listener on the Button, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get query text
                EditText userQuery = (EditText) findViewById(R.id.search_view);
                query = GB_REQUEST_URL + userQuery.getText().toString() + GB_REQUEST_URL_MR;
                Log.v(LOG_TAG, query);

                // Increase loader id by 1
                booklisting_loader_id++;

                // Kick of the user query
                kickQuery();
            }
        });
    }

    private void kickQuery() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(booklisting_loader_id, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<BookListing>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookListingLoader(this, query);
    }

    @Override
    public void onLoadFinished(Loader<List<BookListing>> loader, List<BookListing> bookListings) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No book listings found."
        mEmptyStateTextView.setText(R.string.no_booklisting);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link BookListing}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookListings != null && !bookListings.isEmpty()) {
            mAdapter.addAll(bookListings);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<BookListing>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}