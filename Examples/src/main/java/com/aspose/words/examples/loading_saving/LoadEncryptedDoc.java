package com.aspose.words.examples.loading_saving;

import com.aspose.words.Document;
import com.aspose.words.LoadOptions;
import com.aspose.words.examples.Utils;

public class LoadEncryptedDoc {
    public static void main(String[] args) throws Exception {

        //ExStart:LoadEncryptedDoc
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(LoadEncryptedDoc.class);

        // Load the encrypted document from the absolute path on disk.
        Document doc = new Document(dataDir + "LoadEncrypted.docx", new LoadOptions("aspose"));
        //ExEnd:LoadEncryptedDoc

        System.out.println("Encrypted document loaded successfully.");
    }
}
