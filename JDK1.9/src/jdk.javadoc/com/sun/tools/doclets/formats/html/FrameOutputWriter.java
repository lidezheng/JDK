/*
 * Copyright (c) 1997, 2016, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.tools.doclets.formats.html;

import java.io.*;

import com.sun.tools.doclets.formats.html.markup.*;
import com.sun.tools.doclets.internal.toolkit.*;
import com.sun.tools.doclets.internal.toolkit.util.*;

/**
 * Generate the documentation in the Html "frame" format in the browser. The
 * generated documentation will have two or three frames depending upon the
 * number of packages on the command line. In general there will be three frames
 * in the output, a left-hand top frame will have a list of all packages with
 * links to target left-hand bottom frame. The left-hand bottom frame will have
 * the particular package contents or the all-classes list, where as the single
 * right-hand frame will have overview or package summary or class file. Also
 * take care of browsers which do not support Html frames.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 *
 * @author Atul M Dambalkar
 */
@Deprecated
public class FrameOutputWriter extends HtmlDocletWriter {

    /**
     * Number of packages specified on the command line.
     */
    int noOfPackages;

    /**
     * Constructor to construct FrameOutputWriter object.
     *
     * @param filename File to be generated.
     */
    public FrameOutputWriter(ConfigurationImpl configuration,
                             DocPath filename) throws IOException {
        super(configuration, filename);
        noOfPackages = configuration.packages.size();
    }

    /**
     * Construct FrameOutputWriter object and then use it to generate the Html
     * file which will have the description of all the frames in the
     * documentation. The name of the generated file is "index.html" which is
     * the default first file for Html documents.
     * @throws DocletAbortException
     */
    public static void generate(ConfigurationImpl configuration) {
        FrameOutputWriter framegen;
        DocPath filename = DocPath.empty;
        try {
            filename = DocPaths.INDEX;
            framegen = new FrameOutputWriter(configuration, filename);
            framegen.generateFrameFile();
            framegen.close();
        } catch (IOException exc) {
            configuration.standardmessage.error(
                        "doclet.exception_encountered",
                        exc.toString(), filename);
            throw new DocletAbortException(exc);
        }
    }

    /**
     * Generate the constants in the "index.html" file. Print the frame details
     * as well as warning if browser is not supporting the Html frames.
     */
    protected void generateFrameFile() throws IOException {
        Content frame = getFrameDetails();
        HtmlTree body = new HtmlTree(HtmlTag.BODY);
        body.addAttr(HtmlAttr.ONLOAD, "loadFrames()");
        if (configuration.allowTag(HtmlTag.MAIN)) {
            HtmlTree main = HtmlTree.MAIN(frame);
            body.addContent(main);
        } else {
            body.addContent(frame);
        }
        if (configuration.windowtitle.length() > 0) {
            printFramesDocument(configuration.windowtitle, configuration,
                    body);
        } else {
            printFramesDocument(configuration.getText("doclet.Generated_Docs_Untitled"),
                    configuration, body);
        }
    }

    /**
     * Get the frame sizes and their contents.
     *
     * @return a content tree for the frame details
     */
    protected Content getFrameDetails() {
        HtmlTree leftContainerDiv = new HtmlTree(HtmlTag.DIV);
        HtmlTree rightContainerDiv = new HtmlTree(HtmlTag.DIV);
        leftContainerDiv.addStyle(HtmlStyle.leftContainer);
        rightContainerDiv.addStyle(HtmlStyle.rightContainer);
        if (noOfPackages <= 1) {
            addAllClassesFrameTag(leftContainerDiv);
        } else if (noOfPackages > 1) {
            addAllPackagesFrameTag(leftContainerDiv);
            addAllClassesFrameTag(leftContainerDiv);
        }
        addClassFrameTag(rightContainerDiv);
        HtmlTree mainContainer = HtmlTree.DIV(HtmlStyle.mainContainer, leftContainerDiv);
        mainContainer.addContent(rightContainerDiv);
        return mainContainer;
    }

    /**
     * Add the IFRAME tag for the frame that lists all packages.
     *
     * @param contentTree the content tree to which the information will be added
     */
    private void addAllPackagesFrameTag(Content contentTree) {
        HtmlTree frame = HtmlTree.IFRAME(DocPaths.OVERVIEW_FRAME.getPath(),
                "packageListFrame", configuration.getText("doclet.All_Packages"));
        HtmlTree leftTop = HtmlTree.DIV(HtmlStyle.leftTop, frame);
        contentTree.addContent(leftTop);
    }

    /**
     * Add the IFRAME tag for the frame that lists all classes.
     *
     * @param contentTree the content tree to which the information will be added
     */
    private void addAllClassesFrameTag(Content contentTree) {
        HtmlTree frame = HtmlTree.IFRAME(DocPaths.ALLCLASSES_FRAME.getPath(),
                "packageFrame", configuration.getText("doclet.All_classes_and_interfaces"));
        HtmlTree leftBottom = HtmlTree.DIV(HtmlStyle.leftBottom, frame);
        contentTree.addContent(leftBottom);
    }

    /**
     * Add the IFRAME tag for the frame that describes the class in detail.
     *
     * @param contentTree the content tree to which the information will be added
     */
    private void addClassFrameTag(Content contentTree) {
        HtmlTree frame = HtmlTree.IFRAME(configuration.topFile.getPath(), "classFrame",
                configuration.getText("doclet.Package_class_and_interface_descriptions"));
        frame.addStyle(HtmlStyle.rightIframe);
        contentTree.addContent(frame);
    }
}
