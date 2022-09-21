using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createBuildConfigTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "build_config",
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
                    type = table.Column<int>(nullable: false),
                    madeDept = table.Column<string>(maxLength: 50, nullable: true),
                    maker = table.Column<string>(maxLength: 30, nullable: true),
                    issueDate = table.Column<DateTime>(nullable: true),
                    version = table.Column<string>(maxLength: 20, nullable: true),
                    description = table.Column<string>(maxLength: 100, nullable: true),
                    validDate = table.Column<DateTime>(nullable: true),
                    approval = table.Column<string>(maxLength: 30, nullable: true),
                    auditor = table.Column<string>(maxLength: 30, nullable: true),
                    rdDeptMaster = table.Column<string>(maxLength: 30, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_build_config", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_build_config_companyId_year_type",
                table: "build_config",
                columns: new[] { "companyId", "year", "type" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "build_config");
        }
    }
}
