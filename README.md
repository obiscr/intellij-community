# IntelliJ IDEA Git Client

This project originated as a [YouTrack issue](https://youtrack.jetbrains.com/issue/IJPL-72504/Make-git-client-a-standalone-app
), and it based on the Git client developed by [IntelliJ Community](https://github.com/JetBrains/intellij-community), which is still under development.

<img width="1400" alt="image" src="https://github.com/obiscr/intellij-community/assets/28687074/315ab9bf-c9c0-41b6-94a8-a54eb9358abd">

<img width="1400" alt="image" src="https://github.com/obiscr/intellij-community/assets/28687074/bf7a2b0a-e6fc-402b-a6a1-54cc72c54b53">

## Join Community

[#git-client](https://join.slack.com/t/jetbrains-platform/shared_invite/zt-2ijy6xdcm-AeyiqKg2vR9BFORlbbn~MQ) channel on JetBrains Platform Slack 

## How to Run project locally

### Clone repository

Since the repository is very large, we specify the branch as `git-client` when we clone it.

> [!NOTE]  
> If you just want the code and don't care about the history of commits. You can also add `--depth=1` for faster cloning. This will ignore the historical commit messages.

```bash
mkdir -p ~/projects
cd ~/projects
git clone -b git-client https://github.com/obiscr/intellij-community.git intellij-community-git-client
cd intellij-community-git-client
```

### Clone submodules

#### Windows

```bash
.\getPlugins.bat
```

#### macOS / Linux

```bash
sh getPlugins.sh
```

### Open Project

Open **intellij-community-git-client** in [IntelliJ IDEA](https://www.jetbrains.com/idea/download) (IC/IU), If it's the first time you open it, it usually fails to index. This is because it requires setting up the runtime environment.

Open `File` - `Project Structure`, select **Project** Item on the left sidebar, select **Download JDK**

![image](https://github.com/obiscr/intellij-community/assets/28687074/26f564b4-e809-4087-8d07-b928538dafb5)

Select JetBrains Runtime (17.0.7 / 17.0.9) in the popup menu

![image](https://github.com/obiscr/intellij-community/assets/28687074/51f7cf2d-e3f4-4c96-ac04-63b3dab613ab)

Wait for JBR to finish downloading, apply and save. The IDE should start indexing automatically, if not close the IDE and reopen it.

> [!NOTE]  
> This step may take a long time, so please be patient.


### Build & Run

Select IDEA in **Run Congratulations** and click ![](https://intellij-icons.jetbrains.design/icons/AllIcons/actions/execute.svg) or ![](https://intellij-icons.jetbrains.design/icons/AllIcons/actions/startDebugger.svg) on the right to start the project. The compilation task will start automatically, and when it finishes, it will start an IDE in a sandbox environment.

> [!NOTE]  
> This step may take a long time, so please be patient.

![image](https://github.com/obiscr/intellij-community/assets/28687074/3cba890c-0b64-41e6-b6ad-2b1c796bf980)

Congratulations, you have successfully run the IDE locally.
