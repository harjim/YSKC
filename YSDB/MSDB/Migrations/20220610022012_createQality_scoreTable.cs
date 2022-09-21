using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createQality_scoreTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "scoreTime",
                table: "quality_score");

            migrationBuilder.DropColumn(
                name: "scores",
                table: "quality_score");

            migrationBuilder.AlterColumn<DateTime>(
                name: "month",
                table: "quality_score",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "quality_score_log",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    qualityId = table.Column<int>(nullable: false),
                    scoreCount = table.Column<int>(nullable: false),
                    scores = table.Column<string>(maxLength: 500, nullable: true),
                    totalScore = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    avgScore = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    submitTime = table.Column<DateTime>(nullable: false),
                    auditTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_quality_score_log", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "quality_score_log");

            migrationBuilder.AlterColumn<DateTime>(
                name: "month",
                table: "quality_score",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AddColumn<DateTime>(
                name: "scoreTime",
                table: "quality_score",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "scores",
                table: "quality_score",
                maxLength: 500,
                nullable: true);
        }
    }
}
