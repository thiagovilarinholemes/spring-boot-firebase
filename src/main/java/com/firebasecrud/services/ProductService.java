package com.firebasecrud.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import com.firebasecrud.entities.Product;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class ProductService {

	private static final String COLLECTION_NAME = "product";
	
	public List<Product> findAll() throws ExecutionException, InterruptedException {	
		ApiFuture<QuerySnapshot> future =
				FirestoreClient.getFirestore().collection(COLLECTION_NAME).whereEqualTo("timestampDelete", null).get(); // .whereEqualTo("timestampDelete", true).get();		
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Product> listProduct = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
        	Product p = new Product();
        	p.setId(document.getId());
        	p.setName(document.getString("name"));
        	p.setDescription(document.getString("description"));
        	listProduct.add(p);
//        	listProduct.add(document.toObject(Product.class)); // Não captura ID       	
        }
        return listProduct;
	}
	
	public Map<String, Object> findById(String id) throws ExecutionException, InterruptedException{
		DocumentReference docRef = FirestoreClient.getFirestore().collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
        return document.getData();		
	}
	
	public void save(Product product) throws ExecutionException, InterruptedException{
		DocumentReference addedDocRef = FirestoreClient.getFirestore().collection(COLLECTION_NAME).document();
		product.setTimestampCreate(Timestamp.now());
		product.setTimestampDelete(null);
		product.setTimestampUpdate(null);
		product.setId(addedDocRef.getId());
		ApiFuture<WriteResult> create = addedDocRef.set(product);
//		ApiFuture<WriteResult> createTimestamp = addedDocRef.update("timestampCreate", FieldValue.serverTimestamp());
//		ApiFuture<WriteResult> deleteTimestamp = addedDocRef.update("timestampDelete", false);
//	    ApiFuture<WriteResult> updateTimestamp = addedDocRef.update("timestampUpdate", false);
	    ApiFuture<WriteResult> id = addedDocRef.update("id", addedDocRef.getId());

	}
	
	public void update(String id, Product obj) throws ExecutionException, InterruptedException{
		DocumentReference docRef = (DocumentReference) FirestoreClient.getFirestore().collection(COLLECTION_NAME).document(id);
		ApiFuture<WriteResult> update = docRef.update(
                "name", obj.getName(),
                "description", obj.getDescription(),
                "timestampUpdate", FieldValue.serverTimestamp()
        );
	}
	
	public void delete(String id) {
		DocumentReference docRef = (DocumentReference) FirestoreClient.getFirestore().collection(COLLECTION_NAME).document(id);
		ApiFuture<WriteResult> delete = docRef.update("timestampDelete", true);
	}
}
