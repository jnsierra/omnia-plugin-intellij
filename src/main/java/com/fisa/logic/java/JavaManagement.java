package com.fisa.logic.java;

import com.fisa.utils.PathsUtils;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;


public class JavaManagement {
    private String fatherEntity;
    private Project project;
    private VirtualFile targetDir;

    public JavaManagement(String fatherEntity) {
        this.fatherEntity = fatherEntity;
        this.getActualProject();
    }

    public static void addImportToFile(Project project, PsiJavaFile javaFile, String qualifiedClassName) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);

            // Obtener la clase a partir del nombre calificado completo
            PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(qualifiedClassName, javaFile.getResolveScope());
            if (psiClass != null) {
                // Crear la declaración de importación
                PsiImportStatement importStatement = elementFactory.createImportStatement(psiClass);
                // Añadir la importación al archivo Java
                javaFile.getImportList().add(importStatement);
            }
        });
    }

    public boolean addFatherParameter(){
        PsiFile psiFile = PsiManager.getInstance(project).findFile(targetDir);
        if(psiFile instanceof PsiJavaFile){
            PsiJavaFile javaFile = (PsiJavaFile) psiFile;
            PsiClass[] classes = javaFile.getClasses();
            if (classes.length > 0) {
                PsiClass psiClass = classes[0];
                PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();

                // Crear el campo 'private String name;'
                PsiField newField = elementFactory.createFieldFromText("private Set<EmailEntity> emails;", psiClass);
                PsiClass psiClassImpSet = JavaPsiFacade.getInstance(project).findClass("java.util.Set", javaFile.getResolveScope());
                PsiClass psiClassImpMany = JavaPsiFacade.getInstance(project).findClass("jakarta.persistence.OneToMany", javaFile.getResolveScope());

                // Añadir el campo a la clase
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    psiClass.add(newField);
                    if (psiClassImpSet != null) {
                        // Crear la declaración de importación
                        PsiImportStatement importStatementSet = elementFactory.createImportStatement(psiClassImpSet);
                        PsiImportStatement importStatementMay = elementFactory.createImportStatement(psiClassImpMany);
                        // Añadir la importación al archivo Java
                        javaFile.getImportList().add(importStatementSet);
                        javaFile.getImportList().add(importStatementMay);
                    }

                });
            }
        }
        return false;
    }
    private void getActualProject(){
        this.project = ProjectManager.getInstance().getOpenProjects()[0];

        if(project == null){
            System.out.println("No project is currently open.");
            throw new RuntimeException("No project is currently open.");
        }

        VirtualFile projectBaseDir = project.getBaseDir();

        this.targetDir = projectBaseDir.findFileByRelativePath(String.format("%s/%sEntity.java",PathsUtils.PATH_TEMPLATE_TARGET_ENTITY,fatherEntity));

        if (targetDir == null) {
            throw new RuntimeException("Entidad no existetne ");
        }
    }
}
