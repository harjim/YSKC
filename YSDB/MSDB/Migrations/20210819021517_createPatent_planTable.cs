using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace MSDB.Migrations
{
    public partial class createPatent_planTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "patent_plan",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    customerId = table.Column<int>(nullable: false),
                    patentNo = table.Column<string>(maxLength: 50, nullable: true),
                    patentName = table.Column<string>(maxLength: 200, nullable: false),
                    patentType = table.Column<int>(nullable: false),
                    unitType = table.Column<int>(nullable: false),
                    planType = table.Column<int>(nullable: false),
                    year = table.Column<int>(nullable: false),
                    projectId = table.Column<int>(nullable: false),
                    code = table.Column<string>(maxLength: 50, nullable: false),
                    linkMan = table.Column<string>(maxLength: 50, nullable: false),
                    linkTel = table.Column<string>(maxLength: 100, nullable: false),
                    postCode = table.Column<string>(maxLength: 10, nullable: true),
                    otherItem = table.Column<string>(maxLength: 10, nullable: true),
                    address = table.Column<string>(maxLength: 200, nullable: true),
                    firstInventor = table.Column<string>(maxLength: 200, nullable: true),
                    otherInventor = table.Column<string>(maxLength: 300, nullable: true),
                    masterId = table.Column<int>(nullable: true),
                    process = table.Column<int>(nullable: false),
                    status = table.Column<int>(nullable: true),
                    ownerId = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_patent_plan", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "patent_plan");
        }
    }
}
