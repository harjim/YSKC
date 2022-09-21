using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createRsProject_uncollectedTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "rsProject_uncollected",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    rsProjectId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    rdTitle = table.Column<string>(maxLength: 50, nullable: true),
                    pname = table.Column<string>(maxLength: 200, nullable: true),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_rsProject_uncollected", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_rsProject_uncollected_companyId_rsProjectId_year",
                table: "rsProject_uncollected",
                columns: new[] { "companyId", "rsProjectId", "year" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "rsProject_uncollected");
        }
    }
}
