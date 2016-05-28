/*
 * Copyright 2001-2014 Aspose Pty Ltd. All Rights Reserved.
 *
 * This file is part of Aspose.Words. The source code in this file
 * is only intended as a supplement to the documentation, and is provided
 * "as is", without warranty of any kind, either expressed or implied.
 */

package com.aspose.words.examples.programming_documents.fields;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FormFieldCollection;
import com.aspose.words.examples.Utils;

public class FormFieldsGetFormFieldsCollection
{
    public static void main(String[] args) throws Exception
    {
        //ExStart:1
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(FormFieldsGetFormFieldsCollection.class);

        Document doc = new Document(dataDir + "FormFields.doc");
        FormFieldCollection formFields = doc.getRange().getFormFields();
        doc.save(dataDir + "Output.docx");
        //ExEnd:1
   }
}




