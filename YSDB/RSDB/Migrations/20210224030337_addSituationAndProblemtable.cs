using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addSituationAndProblemtable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_problem_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    situationId = table.Column<int>(nullable: false),
                    problem = table.Column<string>(maxLength: 500, nullable: true),
                    Solution = table.Column<string>(maxLength: 500, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_problem_summary", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "p_situation_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    annualReview = table.Column<string>(maxLength: 1000, nullable: true),
                    teamEffort = table.Column<string>(maxLength: 1000, nullable: true),
                    scheduleReview = table.Column<string>(maxLength: 1000, nullable: true),
                    valueRealization = table.Column<string>(maxLength: 1000, nullable: true),
                    improvementPoints = table.Column<string>(maxLength: 1000, nullable: true),
                    rdDepts = table.Column<string>(maxLength: 1000, nullable: true),
                    rdContent = table.Column<string>(maxLength: 1000, nullable: true),
                    achievements = table.Column<string>(maxLength: 1000, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_situation_summary", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_problem_summary");

            migrationBuilder.DropTable(
                name: "p_situation_summary");
        }
    }
}
