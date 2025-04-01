# Git & GitHub Cheatsheet for Beginners

> Written by [dmarakom6](https://github.com/dmarakom6) and assuming that you have [installed git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).

## Setup
```bash
# Configure your identity
git config --global user.name "Giannis Giannakis"
git config --global user.email "ggianakis@email.com"

# Initialize a new repository
git init

# Clone existing repository
git clone https://github.com/user/repo.git
```

## Basic Workflow
```bash
# Check status of changes
git status

# Add files to staging area
git add filename.java   # Specific file
git add .               # All changes

# Commit changes
git commit -m "Descriptive message"

# View commit history
git log
```

## Branching

### Why Branching?

#### 1. **Safe Experimentation**  
Branches let you try new ideas (like implementing a new class or algorithm) without breaking the working version of your code. If your experiment fails, just delete the branch.

#### 2. **Parallel Work**  
- Multiple team members can work on different tasks simultaneously:  
  - One branch for UI improvements 🎨  
  - Another branch for bug fixes 🐛  
  - Main branch stays stable ✅  

#### 3. **Feature Isolation**  
Each new feature (e.g., adding file I/O functionality) gets its own branch. This keeps changes organized and makes debugging easier.

#### 4. **Version Control**  
Maintain separate branches for:  
- Production-ready code (`main`)  
- Development work (`dev`)  
- Hotfixes for urgent issues (`hotfix-xxx`)  


**Key Benefit:** Your main project stays functional even while adding complex new features.

```bash
# Create new branch
git branch branch-name

# Switch branches
git checkout branch-name

# Create and switch to new branch
git checkout -b new-branch

# Merge branches
git merge branch-name
```

## Remote Operations
```bash
# Connect to remote repository
git remote add origin https://github.com/user/repo.git

# Push changes to GitHub
git push origin main

# Pull latest changes
git pull origin main

# Fetch changes without merging
git fetch
```

## Undoing Changes
```bash
# Unstage a file
git reset HEAD filename.java

# Amend last commit
git commit --amend

# Revert a commit
git revert commit-id

# Discard local changes
git checkout -- filename.java
```

## Collaboration Basics
1. **Fork** a repository on GitHub
2. **Clone** your forked repository
3. Create a **pull request** after pushing changes
4. Sync your fork with original repo (over https):
```bash
git remote add upstream https://github.com/original/repo.git
git fetch upstream
git merge upstream/main
```

## Helpful Tips
- Use `.gitignore` file to exclude files (e.g., `.class`, `bin/`)
- Commit often with meaningful messages, please.
- Pull before pushing to avoid conflicts (or just do `git push --force`)
- Use `git diff` or `git status` to see changes before committing

***Thanks for reading!***
