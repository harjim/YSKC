using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace ESDB.Migrations
{
    public partial class createAchievementsAndE_coopreationTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "achievement",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    achievementName = table.Column<string>(maxLength: 100, nullable: false),
                    industry = table.Column<string>(maxLength: 20, nullable: false),
                    researchArea = table.Column<string>(maxLength: 20, nullable: false),
                    type = table.Column<int>(nullable: false),
                    trl = table.Column<int>(nullable: false),
                    startDate = table.Column<DateTime>(nullable: false),
                    endDate = table.Column<DateTime>(nullable: false),
                    negotiation = table.Column<bool>(nullable: false),
                    price = table.Column<decimal>(type: "decimal(18,6)", nullable: true),
                    cooperateType = table.Column<int>(nullable: false),
                    keywords = table.Column<string>(maxLength: 50, nullable: false),
                    source = table.Column<string>(maxLength: 100, nullable: false),
                    job = table.Column<string>(maxLength: 50, nullable: false),
                    ownerName = table.Column<string>(maxLength: 50, nullable: false),
                    tel = table.Column<string>(maxLength: 20, nullable: false),
                    email = table.Column<string>(maxLength: 50, nullable: false),
                    addressCode = table.Column<string>(maxLength: 30, nullable: false),
                    address = table.Column<string>(maxLength: 100, nullable: false),
                    description = table.Column<string>(maxLength: 2000, nullable: false),
                    innovation = table.Column<string>(maxLength: 2000, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_achievement", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "achievement_files",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    userId = table.Column<int>(nullable: false),
                    achievementId = table.Column<int>(nullable: false),
                    achievementFile = table.Column<string>(maxLength: 1000, nullable: false),
                    agent = table.Column<bool>(nullable: false),
                    ownerName = table.Column<string>(maxLength: 50, nullable: false),
                    agentFile = table.Column<string>(maxLength: 200, nullable: true),
                    assess = table.Column<bool>(nullable: false),
                    assessType = table.Column<int>(nullable: true),
                    assessFile = table.Column<string>(maxLength: 200, nullable: true),
                    smallTest = table.Column<bool>(nullable: false),
                    smallTestFile = table.Column<string>(maxLength: 200, nullable: true),
                    pilotTest = table.Column<bool>(nullable: false),
                    pilotTestFile = table.Column<bool>(maxLength: 200, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_achievement_files", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "achievement_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    achievementId = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    suggestion = table.Column<string>(maxLength: 2000, nullable: true),
                    msCreatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_achievement_log", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "e_cooperation",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    intentionUserId = table.Column<int>(nullable: false),
                    unitName = table.Column<string>(maxLength: 100, nullable: false),
                    fullname = table.Column<string>(maxLength: 20, nullable: false),
                    linkInfo = table.Column<string>(maxLength: 50, nullable: false),
                    remark = table.Column<string>(maxLength: 500, nullable: true),
                    status = table.Column<int>(nullable: false),
                    infomation = table.Column<string>(maxLength: 500, nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: true),
                    msLastUpdatorId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_e_cooperation", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "achievement");

            migrationBuilder.DropTable(
                name: "achievement_files");

            migrationBuilder.DropTable(
                name: "achievement_log");

            migrationBuilder.DropTable(
                name: "e_cooperation");
        }
    }
}
