package com.aspose.words.examples.programming_documents.comments;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.examples.Utils;

@SuppressWarnings("unchecked")
public class CreateSimpleDocumentUsingDocumentBuilder {
    public static void main(String[] args) throws Exception {

        //ExStart:CreateSimpleDocumentUsingDocumentBuilder
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(CreateSimpleDocumentUsingDocumentBuilder.class);

        // Open the document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("Aspose_Words_Java");
        doc.save(dataDir + "output.doc");
        //ExEnd:CreateSimpleDocumentUsingDocumentBuilder

    }
}