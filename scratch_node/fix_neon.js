const { Client } = require('pg');

async function testPassword(password) {
  // Use config object instead of string to avoid URL parsing issues!
  const client = new Client({
    host: 'ep-late-haze-aznqt0e.c-3.ap-southeast-1.aws.neon.tech',
    port: 5432,
    database: 'neondb',
    user: 'neondb_owner',
    password: password,
    ssl: { rejectUnauthorized: false } // Required for neon
  });

  try {
    await client.connect();
    console.log("SUCCESS! Password is:", password);
    await client.end();
    return true;
  } catch (err) {
    if (err.message.includes("password authentication failed")) {
      console.log("Failed:", password);
    } else {
      console.log("Other error with", password, ":", err.message);
    }
    return false;
  }
}

async function run() {
  const passwordsToTry = [
    "Chr!stEmbassyPune2026#Secure",
    "Chr!stEmbassyPune2026",
    "Chr!stEmbassyPune2026#",
    "Chr!stEmbassyPune2026!",
    "Chr!stEmbassyPune2026@",
    "Chr!stEmbassyPune2026$",
    "Chr!stEmbassyPune",
    "Chr!stEmbassyPune2026#Secure!",
    "NewCePunePass2026!"
  ];

  for (const p of passwordsToTry) {
    const success = await testPassword(p);
    if (success) {
      process.exit(0);
    }
  }
  console.log("None of the passwords worked.");
}

run();
