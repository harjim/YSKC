using Microsoft.EntityFrameworkCore.Migrations;

namespace STSDB.Migrations
{
    public partial class addCompanyOrgIdCol : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "orgId",
                table: "company",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "orgId",
                table: "company");
        }
    }
}
