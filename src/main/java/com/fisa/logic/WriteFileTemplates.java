package com.fisa.logic;

import com.fisa.dto.TemplateResultDto;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiManager;

public class WriteFileTemplates {

    private static WriteFileTemplates instance;

    private WriteFileTemplates(){}

    public static WriteFileTemplates getInstance() {
        if (instance == null) {
            instance = new WriteFileTemplates();
        }
        return instance;
    }

    public boolean write(TemplateResultDto templateResultDto){
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        if(project == null){
            System.out.println("No project is currently open.");
            return false;
        }

        VirtualFile projectBaseDir = project.getBaseDir();
        VirtualFile targetDir = projectBaseDir.findFileByRelativePath(templateResultDto.getTemplatePath());

        if (targetDir == null) {
            return false;
        }

        PsiDirectory psiDirectory = PsiManager.getInstance(project).findDirectory(targetDir);
        if (psiDirectory == null) {
            return false;
        }

        // Crear el archivo con un nombre específico
        // Ejecutar la creación del archivo dentro de una WriteCommandAction
        WriteCommandAction.runWriteCommandAction(project, () -> {
            String fileName = templateResultDto.getTemplateName();

            // Contenido del archivo
            String fileContent = templateResultDto.getTemplateContent();

            // Crear el archivo con contenido
            PsiFile psiFile = PsiFileFactory.getInstance(project)
                    //.createFileFromText(fileName, psiDirectory.getFile().getFileType(), fileContent);
                    .createFileFromText(fileName, psiDirectory.getFiles()[0].getFileType(), fileContent);

            // Agregar el archivo a la carpeta destino
            psiDirectory.add(psiFile);
        });
        return true;
    }
}
