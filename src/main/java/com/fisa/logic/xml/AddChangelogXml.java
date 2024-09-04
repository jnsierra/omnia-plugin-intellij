package com.fisa.logic.xml;

import com.fisa.utils.PathsUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.XmlElementFactory;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.openapi.command.WriteCommandAction;

public class AddChangelogXml {

    public boolean addNewTableChangeLog(String url){
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        if(project == null){
            System.out.println("No project is currently open.");
            return false;
        }

        VirtualFile projectBaseDir = project.getBaseDir();
        VirtualFile targetDir = projectBaseDir.findFileByRelativePath(PathsUtils.PATH_XML_CHANGELOG_LIQUIDBASE);

        if (targetDir == null) {
            return false;
        }
        addXmlTag(project, targetDir, "include", url);
        return true;
    }

    public void addXmlTag(Project project, VirtualFile file, String tagName, String tagValue) {
        // Obtener el archivo PSI
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        if (!(psiFile instanceof XmlFile)) {
            return;
        }

        XmlFile xmlFile = (XmlFile) psiFile;

        XmlElementFactory xmlElementFactory = XmlElementFactory.getInstance(project);
        XmlTag newTag = xmlElementFactory.createTagFromText("<" + tagName + " file=\""+tagValue+"\" />");

        // Añadir la etiqueta al archivo XML

        WriteCommandAction.runWriteCommandAction(project, () -> {
            XmlTag rootTag = xmlFile.getRootTag();
            if (rootTag != null) {
                rootTag.addSubTag(newTag, false);
            }
        });
    }

}