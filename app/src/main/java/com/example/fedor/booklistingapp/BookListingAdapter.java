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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * An {@link BookListingAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link BookListing} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookListingAdapter extends ArrayAdapter<BookListing> {

    /**
     * Constructs a new {@link BookListingAdapter}.
     *
     * @param context      of the app
     * @param bookListings is the list of bookListings, which is the data source of the adapter
     */
    public BookListingAdapter(Context context, List<BookListing> bookListings) {
        super(context, 0, bookListings);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.booklisting_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of books
        BookListing currentBookListing = getItem(position);

        // Find the TextView with view ID title
        TextView titleView = (TextView) convertView.findViewById(R.id.book_title);
        // Display the title of the current book in that TextView
        titleView.setText(currentBookListing.getTitle());

        // Find the TextView with view ID authors
        TextView authorsView = (TextView) convertView.findViewById(R.id.book_authors);
        // Display the authors of the current book in that TextView
        authorsView.setText("Auhor: " + currentBookListing.getAuthor());

        // Find the TextView with view ID page count
        TextView pageCountView = (TextView) convertView.findViewById(R.id.page_count);
        // Format the int to a string (i.e. "499")
        String pageCount = Integer.toString(currentBookListing.getPageCount());
        // Display the page count of the current book in that TextView
        pageCountView.setText("Pages: " + pageCount);

        // Return the list item view that is now showing the appropriate data
        return convertView;
    }
}