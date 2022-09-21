using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addCustomerCompanyTypeAndGroupIdCols : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "companyType",
                table: "company",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "groupId",
                table: "company",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "companyType",
                table: "company");

            migrationBuilder.DropColumn(
                name: "groupId",
                table: "company");
        }
    }
}
