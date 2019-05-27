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

import java.util.regex.Pattern;

public class ExHeaderFooter extends ApiExampleBase {
    @Test
    public void createFooter() throws Exception {
        //ExStart
        //ExFor:HeaderFooter
        //ExFor:HeaderFooter.#ctor(DocumentBase, HeaderFooterType)
        //ExFor:HeaderFooterCollection
        //ExFor:Story.AppendParagraph
        //ExSummary:Creates a footer using the document object model and inserts it into a section.
        Document doc = new Document();

        HeaderFooter footer = new HeaderFooter(doc, HeaderFooterType.FOOTER_PRIMARY);
        doc.getFirstSection().getHeadersFooters().add(footer);

        // Add a paragraph with text to the footer.
        footer.appendParagraph("TEST FOOTER");

        doc.save(getArtifactsDir() + "HeaderFooter.CreateFooter.doc");
        //ExEnd

        doc = new Document(getArtifactsDir() + "HeaderFooter.CreateFooter.doc");
        Assert.assertTrue(doc.getFirstSection().getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY).getRange().getText().contains("TEST FOOTER"));
    }

    @Test
    public void removeFooters() throws Exception {
        //ExStart
        //ExFor:Section.HeadersFooters
        //ExFor:HeaderFooterCollection
        //ExFor:HeaderFooterCollection.Item(HeaderFooterType)
        //ExFor:HeaderFooter
        //ExFor:HeaderFooterType
        //ExId:RemoveFooters
        //ExSummary:Deletes all footers from all sections, but leaves headers intact.
        Document doc = new Document(getMyDir() + "HeaderFooter.RemoveFooters.doc");

        for (Section section : doc.getSections()) {
            // Up to three different footers are possible in a section (for first, even and odd pages).
            // We check and delete all of them.
            HeaderFooter footer;

            footer = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_FIRST);
            if (footer != null) {
                footer.remove();
            }

            // Primary footer is the footer used for odd pages.
            footer = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);
            if (footer != null) {
                footer.remove();
            }

            footer = section.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_EVEN);
            if (footer != null) {
                footer.remove();
            }
        }

        doc.save(getArtifactsDir() + "HeaderFooter.RemoveFooters.doc");
        //ExEnd
    }

    @Test
    public void setExportHeadersFootersMode() throws Exception {
        //ExStart
        //ExFor:HtmlSaveOptions.ExportHeadersFootersMode
        //ExFor:ExportHeadersFootersMode
        //ExSummary:Demonstrates how to disable the export of headers and footers when saving to HTML based formats.
        Document doc = new Document(getMyDir() + "HeaderFooter.RemoveFooters.doc");

        // Disables exporting headers and footers.
        HtmlSaveOptions saveOptions = new HtmlSaveOptions(SaveFormat.HTML);
        saveOptions.setExportHeadersFootersMode(ExportHeadersFootersMode.NONE);

        doc.save(getArtifactsDir() + "HeaderFooter.DisableHeadersFooters.html", saveOptions);
        //ExEnd

        // Verify that the output document is correct.
        doc = new Document(getArtifactsDir() + "HeaderFooter.DisableHeadersFooters.html");
        Assert.assertFalse(doc.getRange().getText().contains("DYNAMIC TEMPLATE"));
    }

    @Test
    public void replaceText() throws Exception {
        //ExStart
        //ExFor:Document.FirstSection
        //ExFor:Section.HeadersFooters
        //ExFor:HeaderFooterCollection.Item(HeaderFooterType)
        //ExFor:HeaderFooter
        //ExFor:Range.Replace(String, String, FindReplaceOptions)
        //ExSummary:Shows how to replace text in the document footer.
        // Open the template document, containing obsolete copyright information in the footer.
        Document doc = new Document(getMyDir() + "HeaderFooter.ReplaceText.doc");

        HeaderFooterCollection headersFooters = doc.getFirstSection().getHeadersFooters();
        HeaderFooter footer = headersFooters.getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);

        FindReplaceOptions options = new FindReplaceOptions();
        options.setMatchCase(false);
        options.setFindWholeWordsOnly(false);

        footer.getRange().replace("(C) 2006 Aspose Pty Ltd.", "Copyright (C) 2011 by Aspose Pty Ltd.", options);

        doc.save(getArtifactsDir() + "HeaderFooter.ReplaceText.doc");
        //ExEnd

        // Verify that the appropriate changes were made to the output document.
        doc = new Document(getArtifactsDir() + "HeaderFooter.ReplaceText.doc");
        Assert.assertTrue(doc.getRange().getText().contains("Copyright (C) 2011 by Aspose Pty Ltd."));
    }

    @Test
    public void headerFooterOrder() throws Exception {
        //ExStart
        //ExFor:IReplacingCallback
        //ExFor:Range.Replace(String, String, FindReplaceOptions)
        //ExSummary: Show changes for headers and footers order
        Document doc = new Document(getMyDir() + "HeaderFooter.HeaderFooterOrder.docx");

        //Assert that we use special header and footer for the first page
        //The order for this: first header\footer, even header\footer, primary header\footer
        Section firstPageSection = doc.getFirstSection();
        Assert.assertEquals(firstPageSection.getPageSetup().getDifferentFirstPageHeaderFooter(), true);

        FindReplaceOptions options = new FindReplaceOptions();
        ReplaceLog logger = new ReplaceLog();
        options.setReplacingCallback(logger);

        doc.getRange().replace(Pattern.compile("(header|footer)"), "", options);

        Assert.assertEquals(logger.getText(), "First header\r\nFirst footer\r\nSecond header\r\nSecond footer\r\nThird header\r\n" + "Third footer\r\n");

        //Prepare our string builder for assert results without "DifferentFirstPageHeaderFooter"
        logger.clearText();

        //Remove special first page
        //The order for this: primary header, default header, primary footer, default footer, even header\footer
        firstPageSection.getPageSetup().setDifferentFirstPageHeaderFooter(false);

        doc.getRange().replace(Pattern.compile("(header|footer)"), "", options);
        Assert.assertEquals(logger.getText(), "Third header\r\nFirst header\r\nThird footer\r\nFirst footer\r\nSecond header\r\nSecond footer\r\n");
    }

    private static class ReplaceLog implements IReplacingCallback {
        public int replacing(final ReplacingArgs args) {
            mTextBuilder.append(args.getMatchNode().getText() + "\r\n");
            return ReplaceAction.SKIP;
        }

        void clearText() {
            mTextBuilder.setLength(0);
        }

        String getText() {
            return mTextBuilder.toString();
        }

        private StringBuilder mTextBuilder = new StringBuilder();
    }
    //ExEnd

    //ExStart
    //ExId:HeaderFooterPrimer
    //ExSummary:Maybe a bit complicated example, but demonstrates many things that can be done with headers/footers.
    @Test //ExSkip
    public void headerFooterPrimer() throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        Section currentSection = builder.getCurrentSection();
        PageSetup pageSetup = currentSection.getPageSetup();

        // Specify if we want headers/footers of the first page to be different from other pages.
        // You can also use PageSetup.OddAndEvenPagesHeaderFooter property to specify
        // different headers/footers for odd and even pages.
        pageSetup.setDifferentFirstPageHeaderFooter(true);

        // --- Create header for the first page. ---
        pageSetup.setHeaderDistance(20);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_FIRST);
        builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);

        // Set font properties for header text.
        builder.getFont().setName("Arial");
        builder.getFont().setBold(true);
        builder.getFont().setSize(14);
        // Specify header title for the first page.
        builder.write("Aspose.Words Header/Footer Creation Primer - Title Page.");

        // --- Create header for pages other than first. ---
        pageSetup.setHeaderDistance(20);
        builder.moveToHeaderFooter(HeaderFooterType.HEADER_PRIMARY);

        // Insert absolutely positioned image into the top/left corner of the header.
        // Distance from the top/left edges of the page is set to 10 points.
        String imageFileName = getImageDir() + "Aspose.Words.gif";
        builder.insertImage(imageFileName, RelativeHorizontalPosition.PAGE, 10, RelativeVerticalPosition.PAGE, 10, 50, 50, WrapType.THROUGH);

        builder.getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);
        // Specify another header title for other pages.
        builder.write("Aspose.Words Header/Footer Creation Primer.");

        // --- Create footer for pages other than first. ---
        builder.moveToHeaderFooter(HeaderFooterType.FOOTER_PRIMARY);

        // We use table with two cells to make one part of the text on the line (with page numbering)
        // to be aligned left, and the other part of the text (with copyright) to be aligned right.
        builder.startTable();

        // Clear table borders.
        builder.getCellFormat().clearFormatting();

        builder.insertCell();

        // Set first cell to 1/3 of the page width.
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 / 3));

        // Insert page numbering text here.
        // It uses PAGE and NUMPAGES fields to auto calculate current page number and total number of pages.
        builder.write("Page ");
        builder.insertField("PAGE", "");
        builder.write(" of ");
        builder.insertField("NUMPAGES", "");

        // Align this text to the left.
        builder.getCurrentParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);

        builder.insertCell();
        // Set the second cell to 2/3 of the page width.
        builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 * 2 / 3));

        builder.write("(C) 2001 Aspose Pty Ltd. All rights reserved.");

        // Align this text to the right.
        builder.getCurrentParagraph().getParagraphFormat().setAlignment(ParagraphAlignment.RIGHT);

        builder.endRow();
        builder.endTable();

        builder.moveToDocumentEnd();
        // Make page break to create a second page on which the primary headers/footers will be seen.
        builder.insertBreak(BreakType.PAGE_BREAK);

        // Make section break to create a third page with different page orientation.
        builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

        // Get the new section and its page setup.
        currentSection = builder.getCurrentSection();
        pageSetup = currentSection.getPageSetup();

        // Set page orientation of the new section to landscape.
        pageSetup.setOrientation(Orientation.LANDSCAPE);

        // This section does not need different first page header/footer.
        // We need only one title page in the document and the header/footer for this page
        // has already been defined in the previous section
        pageSetup.setDifferentFirstPageHeaderFooter(false);

        // This section displays headers/footers from the previous section by default.
        // Call currentSection.HeadersFooters.LinkToPrevious(false) to cancel this.
        // Page width is different for the new section and therefore we need to set
        // a different cell widths for a footer table.
        currentSection.getHeadersFooters().linkToPrevious(false);

        // If we want to use the already existing header/footer set for this section
        // but with some minor modifications then it may be expedient to copy headers/footers
        // from the previous section and apply the necessary modifications where we want them.
        copyHeadersFootersFromPreviousSection(currentSection);

        // Find the footer that we want to change.
        HeaderFooter primaryFooter = currentSection.getHeadersFooters().getByHeaderFooterType(HeaderFooterType.FOOTER_PRIMARY);

        Row row = primaryFooter.getTables().get(0).getFirstRow();
        row.getFirstCell().getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 / 3));
        row.getLastCell().getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 * 2 / 3));

        // Save the resulting document.
        doc.save(getArtifactsDir() + "HeaderFooter.Primer.doc");
    }

    /**
     * Clones and copies headers/footers form the previous section to the specified section.
     */
    private static void copyHeadersFootersFromPreviousSection(final Section section) {
        Section previousSection = (Section) section.getPreviousSibling();

        if (previousSection == null) {
            return;
        }

        section.getHeadersFooters().clear();

        for (HeaderFooter headerFooter : previousSection.getHeadersFooters()) {
            section.getHeadersFooters().add(headerFooter.deepClone(true));
        }
    }
    //ExEnd
}

