using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_docFile_analysisTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_docFile_analysis",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    type = table.Column<int>(nullable: false),
                    expectation = table.Column<string>(maxLength: 1000, nullable: true),
                    actuality = table.Column<string>(maxLength: 1000, nullable: true),
                    rewrite = table.Column<bool>(nullable: false),
                    canWrite = table.Column<bool>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_p_docFile_analysis", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_p_docFile_analysis_projectId_type",
                table: "p_docFile_analysis",
                columns: new[] { "projectId", "type" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_docFile_analysis");
        }
    }
}
