package com.fisa.logic.java;

import com.fisa.utils.PathsUtils;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;

public class JavaClassModifierEntity {
    private final String entityName;
    private final String pluralEntity;
    private final String fatherEntity;
    private final Project project;
    private final VirtualFile targetDir;

    public JavaClassModifierEntity(String entityName,String pluralEntity, String fatherEntity) {
        this.fatherEntity = fatherEntity;
        this.entityName = entityName;
        this.project = getActiveProject();
        this.targetDir = findTargetDir();
        this.pluralEntity = pluralEntity;
    }
    public static void addImportToFile(Project project, PsiJavaFile javaFile, String qualifiedClassName) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(qualifiedClassName, javaFile.getResolveScope());
            if (psiClass != null) {
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
                PsiImportStatement importStatement = elementFactory.createImportStatement(psiClass);
                javaFile.getImportList().add(importStatement);
            }
        });
    }

    public boolean addFatherParameter() {
        PsiJavaFile javaFile = getPsiJavaFile(targetDir);
        if (javaFile != null) {
            PsiClass psiClass = getPrimaryPsiClass(javaFile);
            if (psiClass != null) {
                addFieldAndImports(javaFile, psiClass, pluralEntity, entityName);
                return true;
            }
        }
        return false;
    }


    private void addFieldAndImports(PsiJavaFile javaFile, PsiClass psiClass, String fieldName,String entityName) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
            PsiField newField = elementFactory.createFieldFromText(String.format("private Set<%sEntity> %s;", entityName, fieldName ) , psiClass);
            psiClass.add(newField);
            addImportToFile(javaFile, "java.util.Set");
            addImportToFile(javaFile, "jakarta.persistence.OneToMany");

            //Añadimos la anotacion
            // Buscar el campo deseado por nombre

            PsiField field = psiClass.findFieldByName(fieldName, false);
            if (field != null) {
                // Crear la anotación @OneToMany(mappedBy = "userBank")
                PsiModifierList modifierList = field.getModifierList();
                if (modifierList != null) {
                    modifierList.addAnnotation(String.format("OneToMany(mappedBy = \"%s\")", entityName.substring(0,1 ).toLowerCase() + entityName.substring(1)) );
                }
            }


        });
    }

    private void addImportToFile(PsiJavaFile javaFile, String qualifiedClassName) {
        PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(qualifiedClassName, javaFile.getResolveScope());
        if (psiClass != null) {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
            PsiImportStatement importStatement = elementFactory.createImportStatement(psiClass);
            javaFile.getImportList().add(importStatement);
        }
    }

    private Project getActiveProject() {
        Project[] openProjects = ProjectManager.getInstance().getOpenProjects();
        if (openProjects.length == 0) {
            throw new IllegalStateException("No project is currently open.");
        }
        return openProjects[0];
    }

    private VirtualFile findTargetDir() {
        VirtualFile projectBaseDir = project.getBaseDir();
        VirtualFile targetDir = projectBaseDir.findFileByRelativePath(
                String.format("%s/%sEntity.java", PathsUtils.PATH_TEMPLATE_TARGET_ENTITY, fatherEntity)
        );
        if (targetDir == null) {
            throw new IllegalArgumentException("Target entity does not exist.");
        }
        return targetDir;
    }

    private PsiJavaFile getPsiJavaFile(VirtualFile file) {
        PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
        return psiFile instanceof PsiJavaFile ? (PsiJavaFile) psiFile : null;
    }

    private PsiClass getPrimaryPsiClass(PsiJavaFile javaFile) {
        PsiClass[] classes = javaFile.getClasses();
        return classes.length > 0 ? classes[0] : null;
    }
}
