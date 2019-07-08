package Examples;

//////////////////////////////////////////////////////////////////////////
// Copyright (c) 2001-2019 Aspose Pty Ltd. All Rights Reserved.
//
// This file is part of Aspose.Words. The source code in this file
// is only intended as a supplement to the documentation, and is provided
// "as is", without warranty of any kind, either expressed or implied.
//////////////////////////////////////////////////////////////////////////

import com.aspose.words.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;

public class ExInlineStory extends ApiExampleBase {
    @Test
    public void addFootnote() throws Exception {
        //ExStart
        //ExFor:Footnote
        //ExFor:InlineStory
        //ExFor:InlineStory.Paragraphs
        //ExFor:InlineStory.FirstParagraph
        //ExFor:FootnoteType
        //ExFor:Footnote.#ctor
        //ExSummary:Shows how to add a footnote to a paragraph in the document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("Some text is added.");

        Footnote footnote = new Footnote(doc, FootnoteType.FOOTNOTE);
        builder.getCurrentParagraph().appendChild(footnote);
        footnote.getParagraphs().add(new Paragraph(doc));
        footnote.getFirstParagraph().getRuns().add(new Run(doc, "Footnote text."));
        //ExEnd

        Assert.assertEquals(doc.getChildNodes(NodeType.FOOTNOTE, true).get(0).toString(SaveFormat.TEXT).trim(), "Footnote text.");
    }

    @Test
    public void addComment() throws Exception {
        //ExStart
        //ExFor:Comment
        //ExFor:InlineStory
        //ExFor:InlineStory.Paragraphs
        //ExFor:InlineStory.FirstParagraph
        //ExFor:Comment.#ctor(DocumentBase, String, String, DateTime)
        //ExSummary:Shows how to add a comment to a paragraph in the document.
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        builder.write("Some text is added.");

        Comment comment = new Comment(doc, "Amy Lee", "AL", new Date());
        builder.getCurrentParagraph().appendChild(comment);
        comment.getParagraphs().add(new Paragraph(doc));
        comment.getFirstParagraph().getRuns().add(new Run(doc, "Comment text."));
        //ExEnd

        Assert.assertEquals((doc.getChildNodes(NodeType.COMMENT, true).get(0)).getText(), "Comment text.\r");
    }
}
