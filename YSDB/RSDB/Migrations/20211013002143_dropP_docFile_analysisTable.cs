using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class dropP_docFile_analysisTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "p_docFile_analysis");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "p_docFile_analysis",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    actuality = table.Column<string>(maxLength: 1000, nullable: true),
                    canWrite = table.Column<bool>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    expectation = table.Column<string>(maxLength: 1000, nullable: true),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    rewrite = table.Column<bool>(nullable: false),
                    type = table.Column<int>(nullable: false)
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
    }
}
