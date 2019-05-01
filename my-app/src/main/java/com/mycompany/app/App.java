package com.mycompany.app;

import io.redisearch.client.Client;
import io.redisearch.Document;
import io.redisearch.SearchResult;
import io.redisearch.Query;
import io.redisearch.Schema;

/**
 * query RediSearch
 *
 */
public class App 
{
    protected static Client client = new Client("myIndex", "localhost", 6379);

    public static void main( String[] argv )
    {
	final int BATCH_SIZE = 10; // batch size for query results

	if (argv[0].equals("schema")) {
		createSchema();
		System.exit(0);
	}

	// create a query from a string
	Query query = new Query(argv[0]);

	// execute query
	SearchResult results = client.search(query);  // first batch
	long totalResults = results.totalResults;
	System.out.printf("Number of documents: %d\n", totalResults);

	// print results
	int i = 0;
	while (i < totalResults) {
		for (Document doc : results.docs) {
			System.out.printf("%s\n", doc.toString());
			i++;
		}
		if (i < totalResults) {
			// get the next batch
			results = client.search(query.limit(i, BATCH_SIZE));
		}
	}

	System.exit(0);
    }

    protected static void createSchema( ) {

	System.out.println("Creating new schema.");
	// public TextField(String name, double weight, boolean sortable, boolean nostem)
	Schema schema = new Schema()
            .addField(new Schema.TextField("group", 1.0, false, true))
            .addField(new Schema.TextField("code", 1.0, false, true))
            .addTagField("type")
            .addField(new Schema.TextField("qty", 1.0, false, true))
            .addField(new Schema.TextField("days", 1.0, false, true))
            .addField(new Schema.TextField("search", 1.0, false, true));

	client.createIndex(schema, Client.IndexOptions.Default());
    }
}
