package ibf2022.batch2.csf.backend.repositories;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ArchiveRepository {

	@Autowired
	private MongoTemplate mgTemplate;

	private final String A_COL = "archives";

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	// db.archives.insert({
	// 		bundleId: "bundleId"
	// 		date: "dateTimeString"
	// 		title: "title"
	// 		name: "name"
	// 		comments: "comment";
	// 	})
	public String recordBundle(String name, String title, String comment) {

		String bundleId = UUID.randomUUID().toString().substring(0, 8);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"yyyy-MM-dd");
			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			// 	"yyyy-MM-dd HH:mm:ss a");
		LocalDateTime now = LocalDateTime.now();
		String dateTimeString = now.format(formatter);

		Document doc = new Document();
		doc.append("bundleId", bundleId)
			.append("date", dateTimeString)
			.append("title", title)
			.append("name", name)
			.append("comments", comment);

		mgTemplate.insert(doc, A_COL);

		return bundleId;
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method

	// db.getCollection("archives").find({
	// 	bundleId:"366aebab"
	// })
	public Optional<Document> getBundleByBundleId(String id) {

		Criteria criteria = Criteria.where("bundleId").is(id);
		Query query = new Query(criteria);

		return Optional.of(mgTemplate.findOne(query, Document.class, A_COL));
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	// db.archives.find().sort( { date: -1, title: 1 } )
	public List<Document> getBundles(/* any number of parameters here */) {
		
		Query query =Query.query(new Criteria())
							.with(Sort.by(Sort.Direction.DESC, "date"))
							.with(Sort.by(Sort.Direction.ASC, "title"));

		return mgTemplate.find(query, Document.class, A_COL);
	}


}
