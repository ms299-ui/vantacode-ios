/*
 * VantaCode Desktop — 版本号自增脚本。
 * 每次构建安装包前运行：VERSION_NAME / VERSION_CODE / packageVersion 同步自增 minor。
 * 用法：node scripts/bump-version.js
 * 三处保持一致：
 *   - desktop/src/jvmMain/kotlin/com/vantacode/core/platform/PlatformPaths.kt （运行时显示）
 *   - desktop/build.gradle.kts （安装包版本）
 */
const fs = require('fs');
const path = require('path');
const ROOT = path.resolve(__dirname, '..');

const platformPaths = path.join(ROOT, 'desktop/src/jvmMain/kotlin/com/vantacode/core/platform/PlatformPaths.kt');
const gradleProps = path.join(ROOT, 'desktop/build.gradle.kts');

function readCurrent() {
    const s = fs.readFileSync(platformPaths, 'utf8');
    const m = s.match(/const val VERSION_NAME = "(\d+)\.(\d+)\.(\d+)"/);
    if (!m) throw new Error('VERSION_NAME not found in PlatformPaths.kt');
    const codeM = s.match(/const val VERSION_CODE = (\d+)/);
    return { major: +m[1], minor: +m[2], patch: +m[3], code: codeM ? +codeM[1] : 0 };
}

const cur = readCurrent();
const next = { major: cur.major, minor: cur.minor + 1, patch: 0, code: cur.code + 100 };
const versionName = `${next.major}.${next.minor}.${next.patch}`;
console.log(`${cur.major}.${cur.minor}.${cur.patch} (code ${cur.code})  ->  ${versionName} (code ${next.code})`);

// PlatformPaths.kt
{
    let s = fs.readFileSync(platformPaths, 'utf8');
    s = s.replace(/const val VERSION_NAME = "\d+\.\d+\.\d+"/, `const val VERSION_NAME = "${versionName}"`);
    s = s.replace(/const val VERSION_CODE = \d+/, `const val VERSION_CODE = ${next.code}`);
    fs.writeFileSync(platformPaths, s);
}

// build.gradle.kts
{
    let s = fs.readFileSync(gradleProps, 'utf8');
    s = s.replace(/packageVersion = "\d+\.\d+\.\d+"/, `packageVersion = "${versionName}"`);
    fs.writeFileSync(gradleProps, s);
}

console.log('bumped.');
