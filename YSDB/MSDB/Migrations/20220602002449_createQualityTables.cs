using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createQualityTables : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "project_audit_score");

            migrationBuilder.CreateTable(
                name: "quality_score",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    rsProjectId = table.Column<int>(nullable: true),
                    year = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: true),
                    scoreCount = table.Column<int>(nullable: false),
                    scoreTime = table.Column<DateTime>(nullable: false),
                    engineerId = table.Column<int>(nullable: false),
                    scores = table.Column<string>(maxLength: 500, nullable: true),
                    totalScore = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    avgScore = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    weight = table.Column<decimal>(type: "decimal(5,3)", nullable: false),
                    passRate = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    type = table.Column<int>(nullable: false),
                    isFinal = table.Column<bool>(nullable: false),
                    monthsId = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_quality_score", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "quality_score_month",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    rsProjectId = table.Column<int>(nullable: false),
                    months = table.Column<string>(maxLength: 500, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_quality_score_month", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "quality_type",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    weight = table.Column<decimal>(type: "decimal(5,3)", nullable: false),
                    type = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_quality_type", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "quality_score");

            migrationBuilder.DropTable(
                name: "quality_score_month");

            migrationBuilder.DropTable(
                name: "quality_type");

            migrationBuilder.CreateTable(
                name: "project_audit_score",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    avgScore = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    engineerId = table.Column<int>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    month = table.Column<DateTime>(nullable: true),
                    passRate = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    rsProjectId = table.Column<int>(nullable: true),
                    scoreCount = table.Column<int>(nullable: false),
                    scoreTime = table.Column<DateTime>(nullable: false),
                    scores = table.Column<string>(maxLength: 500, nullable: true),
                    totalScore = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    type = table.Column<int>(nullable: false),
                    weight = table.Column<decimal>(type: "decimal(5,3)", nullable: false),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_project_audit_score", x => x.id);
                });
        }
    }
}
