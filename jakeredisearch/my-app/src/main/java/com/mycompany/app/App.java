package com.mycompany.app;

import io.redisearch.client.Client;
import io.redisearch.Document;
import io.redisearch.SearchResult;
import io.redisearch.Query;
import io.redisearch.Schema;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] argv )
    {
        System.out.println( "Hello World!" );

	Client client = new Client("myIndex", "localhost", 6379);

	// Creating a complex query
	Query q = new Query(argv[0]);

	// run query
	long start = System.currentTimeMillis();
	SearchResult results = client.search(q);
	long totalMilliseconds = System.currentTimeMillis() - start;

	System.out.printf("Number of documents: %d, Query time: %d ms\n", results.totalResults, totalMilliseconds);
	System.exit(0);
    }
}
