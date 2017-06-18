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

/**
 * An {@link BookListing} object contains information related to a single earthquake.
 */
public class BookListing {

    /** Title of the book */
    private String mTitle;

    /** Author of the book */
    private String mAuthor;

    /** Amount of pages in the book */
    private int mPageCount;

    /** Website URL of the book */
    private String mUrl;

    /**
     * Constructs a new {@link BookListing} object.
     *
     * @param title is the name of the book
     * @param author is the author of the book
     * @param pageCount is the amount of pages in the book
     * @param url is the website URL to find more details about the book
     */
    public BookListing(String title, String author, int pageCount, String url) {
        mTitle = title;
        mAuthor = author;
        mPageCount = pageCount;
        mUrl = url;
    }

    /**
     * Returns the name of the book.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the author of the book.
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Returns the amount of pages in the book.
     */
    public int getPageCount() {
        return mPageCount;
    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }
}