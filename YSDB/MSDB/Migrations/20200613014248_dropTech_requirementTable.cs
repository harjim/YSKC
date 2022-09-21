using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class dropTech_requirementTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tech_requirement");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "tech_requirement",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    companyId = table.Column<int>(nullable: false),
                    cooperateType = table.Column<string>(maxLength: 50, nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    creatorId = table.Column<int>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    description = table.Column<string>(maxLength: 300, nullable: false),
                    domain = table.Column<string>(maxLength: 50, nullable: false),
                    filePath = table.Column<string>(maxLength: 500, nullable: false),
                    investment = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    linkEmail = table.Column<string>(maxLength: 50, nullable: false),
                    linkName = table.Column<string>(maxLength: 20, nullable: false),
                    linkTel = table.Column<string>(maxLength: 50, nullable: false),
                    otherCooperateType = table.Column<string>(maxLength: 50, nullable: true),
                    otherDomain = table.Column<string>(maxLength: 50, nullable: true),
                    position = table.Column<string>(maxLength: 50, nullable: false),
                    requirement = table.Column<string>(maxLength: 300, nullable: false),
                    techName = table.Column<string>(maxLength: 300, nullable: false),
                    year = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tech_requirement", x => x.id);
                });
        }
    }
}
